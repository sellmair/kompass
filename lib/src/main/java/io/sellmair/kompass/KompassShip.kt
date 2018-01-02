package io.sellmair.kompass

import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentManager


/**
 * Created by sebastiansellmair on 06.12.17.
 */
interface KompassShip<in Destination> {
    fun <T : Destination> navigateTo(destination: T, replaceCurrent: Boolean = false)
    fun setSail(activity: FragmentActivity, containerId: Int, fragmentManager: FragmentManager? = null): KompassSail
}

