package io.sellmair.kompass

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import io.sellmair.kompass.annotation.RequiresMainThread
import io.sellmair.kompass.util.applyIf
import io.sellmair.kompass.util.mainThread
import io.sellmair.kompass.util.requireMainThread

/**
 * Created by sebastiansellmair on 02.01.18.
 */
internal class BaseShip<in Destination : Any>
(private val kompass: BaseKompass<Destination>) : KompassShip<Destination> {


    private val sailReference = BaseSailReference(this)

    /**
     * This holds all [Commission] objects that are pending (obviously, haha ^^ )
     * Those should be executed once a executeCommission is set via [setSail].
     */
    private val pendingCommissions = mutableListOf<Commission>()


    override fun <T : Destination> navigateTo(destination: T, replaceCurrent: Boolean) {
        /*
        Replace current will use the 'startAt' function.
        This might sound wrong, but this re-assembles the function it had before!
         */
        if (replaceCurrent) startAt(destination)
        else navigateTo(destination)
    }

    override fun <T : Destination> navigateTo(destination: T) {
        navigateTo(destination, Instruction.ADD)
    }

    override fun <T : Destination> beamTo(destination: T) {
        navigateTo(destination, Instruction.REPLACE)
    }

    override fun <T : Destination> startAt(destination: T) {
        navigateTo(destination, Instruction.START)
    }

    override fun popBackImmediate() {
        kompass.popBackImmediate(this)
    }

    private fun <T : Destination> navigateTo(destination: T, instruction: Instruction) {
        /**
         * The captain knows where how to get to the destination.
         * It whether contains an intent as [IntentCaptain] or fragment as [FragmentCaptain]
         */
        val captain = kompass.getCaptain(destination)

        /**
         * Retrieve executeCommission reference,
         * Add the [Commission] to [pendingCommissions] if it cannot be
         * executed, because of a missing executeCommission.
         * The navigation will be executed once a new executeCommission was set via [setSail]
         */
        val sail = sailReference.get() ?: synchronized(this) {
            pendingCommissions.add(Commission(captain, instruction))
            return
        }

        /**
         * Execute the sailing on the main thread
         */
        mainThread {
            executeCommission(sail, Commission(captain, instruction))
        }
    }


    @RequiresMainThread
    override fun setSail(activity: FragmentActivity,
                         containerId: Int,
                         fragmentManager: FragmentManager?): KompassSail {
        requireMainThread()

        /**
         * The [BaseSail] is constructed using the [FragmentActivity.getSupportFragmentManager]
         * fragment manager as default
         */
        val sail = BaseSail(
                baseShip = this,
                activity = activity,
                fragmentManager = fragmentManager ?: activity.supportFragmentManager,
                containerId = containerId)


        /**
         * Hold a weak reference to the executeCommission
         */
        this.sailReference.set(sail)


        /**
         * Execute all pending [Commission] objects
         */
        synchronized(this) {
            pendingCommissions.forEach { commission ->
                executeCommission(sail, commission)
            }

            pendingCommissions.clear()
        }

        return sail
    }

    internal fun releaseSail(sail: KompassSail) {
        if (this.sailReference.get() == sail) {
            this.sailReference.release()
        }
    }

    @RequiresMainThread
    private fun executeCommission(sail: KompassSail, commission: Commission) {
        return when (commission.captain) {
            is IntentCaptain -> sailIntent(sail, commission.captain, commission.instruction)
            is FragmentCaptain -> sailFragment(sail, commission.captain, commission.instruction)
        }
    }

    @RequiresMainThread
    private fun sailIntent(sail: KompassSail, captain: IntentCaptain, instruction: Instruction) {
        sail.activity.startActivity(captain.intent)

        if (instruction == Instruction.REPLACE || instruction == Instruction.START) {
            sail.activity.finish()
        }
    }


    @RequiresMainThread
    private fun sailFragment(sail: KompassSail, captain: FragmentCaptain, instruction: Instruction) {
        /**
         * Just retrieve references to objects and give them more
         * meaningful names
         */
        val fragmentManager = sail.fragmentManager
        val containerId = sail.containerId
        val nextFragment = captain.fragment
        val destination = captain.destination
        val currentFragment = fragmentManager.findFragmentById(containerId)


        /**
         * Clear the entire Kompass-Based backstack.
         * We do not want to pop the back-stack of the [FragmentManager],
         * because this would break the animation.
         *
         * Just removing the entries from the kompass-backstack will make the outdated
         * [FragmentManager.BackStackEntry] objects inaccessible from Kompass anymore.
         *
         * This is the reason, why calling the super [FragmentActivity.onBackPressed] after
         * kompass returned false is a bad idea, because it will still pop the 'dark side' of the
         * backstack
         */
        if (instruction == Instruction.START) {
            kompass.removeFromBackStack(this)
        }

        /**
         * The replace function currently breaks the animations by popping the backstack
         * first, before navigating to the new destination.
         * This could be improved!
         */
        if (instruction == Instruction.REPLACE) {
            kompass.popBackImmediate(this)
        }


        /**
         * If we are just adding something to the backstack,
         * we need to implement a way to get back to the previous state.
         *
         * [FragmentManager.popBackStackImmediate] looks like a good choice,
         * because it respects all [KompassDetour] objects applied to it
         */
        if (instruction == Instruction.ADD) {
            kompass.onBack(key = this) {
                fragmentManager.popBackStackImmediate();
                return@onBack true
            }
        }

        /**
         * Finally create the transaction
         * and apply it
         */
        val transaction = fragmentManager
                .beginTransaction()
                .replace(containerId, nextFragment)
                .pilotSetup(destination, currentFragment, nextFragment)

                /**
                 * Add the transaction to the back-stack to make the code above work!
                 */
                .applyIf(condition = instruction == Instruction.ADD) { addToBackStack(null) }

        transaction.commit()
    }

    private fun FragmentTransaction.pilotSetup(destination: Any,
                                               currentFragment: Fragment?,
                                               nextFragment: Fragment): FragmentTransaction {
        kompass.pilot.setup(
                destination = destination,
                currentFragment = currentFragment ?: Fragment(),
                nextFragment = nextFragment,
                transaction = this)
        return this
    }


    /**
     * A Commission represents is a construct which knows everything about how
     * to get the route.
     */
    private data class Commission(val captain: Captain, val instruction: Instruction)
}