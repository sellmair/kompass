package io.sellmair.kompass

import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentManager
import io.sellmair.kompass.annotation.RequiresMainThread
import io.sellmair.kompass.util.mainThread


/**
 * Created by sebastiansellmair on 06.12.17.
 */
interface KompassShip<in Destination> {

    @Deprecated("The 'beamTo' param is now a separate method 'beamTo()'")
    fun <T : Destination> navigateTo(destination: T, replaceCurrent: Boolean = false)

    /**
     * Will route to the given destination, once a sail is set.
     * The new destination will be added to the backstack (for this ship).
     * [Kompass.popBack] or [Kompass.popBackImmediate] will result in the current destination
     * to be shown again.
     *
     * @see Kompass.popBack
     * @see Kompass.popBackImmediate
     * @see KompassShip.setSail
     */
    infix fun <T : Destination> navigateTo(destination: T)

    /**
     * Same as
     * @see navigateTo
     */
    operator fun <T : Destination> plusAssign(destination: T)

    /**
     * ### beamTo
     * Will route to the given destination, once a sail is set.
     * The current destination (of this ship) will be replaced by the new one in the backstack.
     * [Kompass.popBack] or [Kompass.popBackImmediate] will result in the previous destination
     * to be shown again
     *
     * ### Warning
     * Beaming to a location requires more effort to get animations straight, at this moment.
     * Currently the current displayed fragment will exit using
     * its original enter-transition (reversed).

     * @see Kompass.popBack
     * @see Kompass.popBackImmediate
     */
    infix fun <T : Destination> beamTo(destination: T)

    /**
     * Same as
     * @see beamTo
     */
    operator fun <T : Destination> remAssign(destination: T)

    /**
     * Will route to the given destination, once a sail is set.
     * The backstack (for this ship) will be cleared before adding the new destination.
     * The new destination is now considered the new starting point.
     * [Kompass.popBack] and [Kompass.popBackImmediate] will result in delegating the 'back' command
     * to a different ship, or the activity again.
     */
    infix fun <T : Destination> startAt(destination: T)


    /**
     * Same as
     * @see startAt
     */
    operator fun <T : Destination> timesAssign(destination: T)


    /**
     * Gives this ship an area to place fragments into.
     * All pending transactions will be executed.
     *
     * Make sure to release the sail, when no longer needed.
     * Kompass won't hold any strong reference to the activity, but unreleased
     * sails *can* lead to subtle bugs.
     */
    fun setSail(activity: FragmentActivity,
                containerId: Int,
                fragmentManager: FragmentManager? = null): KompassSail


    /**
     *  Will pop an element, from this ship, from the backstack.
     *
     *  @see popBackImmediate
     *  @see Kompass.popBack
     *  @see Kompass.popBackImmediate
     */
    fun popBack() {
        mainThread {
            popBackImmediate()
        }
    }


    /**
     * Will pop an element - from this ship - from the backstack.
     * Nothing will happen, if this ship doesn't contain any element.
     */
    @RequiresMainThread
    fun popBackImmediate()
}

