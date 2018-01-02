package io.sellmair.kompass

import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentManager

/**
 * Created by sebastiansellmair on 02.01.18.
 */
internal class BaseSail(private val baseShip: BaseShip<*>,
                        override val activity: FragmentActivity,
                        override val fragmentManager: FragmentManager,
                        override val containerId: Int) : KompassSail {


    override fun release() {
        baseShip.releaseSail(this)
    }
}