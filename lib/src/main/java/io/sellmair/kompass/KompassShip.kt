package io.sellmair.kompass

import android.support.annotation.UiThread
import android.support.v4.app.Fragment

interface KompassShip<in Destination> : KompassRouter<Destination> {
    @UiThread
    fun setSail(sail: KompassSail): KompassReleasable

    @UiThread
    fun retainTransitions(fragment: Fragment)
}



