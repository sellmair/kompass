package io.sellmair.kompass

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentManager
import io.sellmair.kompass.annotation.RequiresMainThread
import io.sellmair.kompass.util.mainThread
import io.sellmair.kompass.util.requireMainThread
import io.sellmair.kompass.util.unit

/**
 * Created by sebastiansellmair on 02.01.18.
 */
internal class BaseShip<in Destination : Any>
(private val kompass: BaseKompass<Destination>) : KompassShip<Destination> {

    private val sailReference = BaseSailReference(this)
    private val pendingCommissions = mutableListOf<Commission>()

    override fun <T : Destination> navigateTo(destination: T, replaceCurrent: Boolean) {
        val captain = kompass.getCaptain(destination)
        val sail = sailReference.get() ?: synchronized(this) {
            return pendingCommissions.add(Commission(captain, replaceCurrent)).unit
        }

        mainThread {
            sail(sail, captain, replaceCurrent)
        }

    }

    @RequiresMainThread
    override fun setSail(activity: FragmentActivity, containerId: Int, fragmentManager: FragmentManager?): KompassSail {
        requireMainThread()
        val sail = BaseSail(
                baseShip = this,
                activity = activity,
                fragmentManager = fragmentManager ?: activity.supportFragmentManager,
                containerId = containerId)

        this.sailReference.set(sail)

        pendingCommissions.forEach { commission ->
            sail(sail, commission.captain, commission.replaceCurrent)
        }

        pendingCommissions.clear()

        return sail
    }

    internal fun releaseSail(sail: KompassSail) {
        if (this.sailReference.get() == sail) {
            this.sailReference.release()
        }
    }

    private fun sail(sail: KompassSail, captain: Captain, replaceCurrent: Boolean) {
        return when (captain) {
            is IntentCaptain -> sailIntent(sail, captain, replaceCurrent)
            is FragmentCaptain -> sailFragment(sail, captain, replaceCurrent)
        }
    }

    private fun sailIntent(sail: KompassSail, captain: IntentCaptain, replaceCurrent: Boolean) {
        sail.activity.startActivity(captain.intent)
        if (replaceCurrent) sail.activity.finish()
    }

    private fun sailFragment(sail: KompassSail, captain: FragmentCaptain, replaceCurrent: Boolean) {
        val fragmentManager = sail.fragmentManager
        var transaction = fragmentManager.beginTransaction()
                .replace(sail.containerId, captain.fragment)

        if (!replaceCurrent) {
            transaction = transaction.addToBackStack(null)
            kompass.onBack(this) { fragmentManager.popBackStack(); true }
        }

        if (replaceCurrent) {
            kompass.removeFromBackStack(this)
        }

        val currentFragment = fragmentManager.findFragmentById(sail.containerId) ?: Fragment()
        kompass.pilot.setup(captain.destination, currentFragment, captain.fragment, transaction)

        transaction.commit()
    }


    private data class Commission(val captain: Captain, val replaceCurrent: Boolean)
}