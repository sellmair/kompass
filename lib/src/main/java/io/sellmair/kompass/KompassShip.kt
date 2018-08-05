package io.sellmair.kompass

import android.support.annotation.UiThread

interface KompassShip<in Destination> : KompassRouter<Destination> {
    @UiThread
    fun setSail(sail: KompassSail): KompassReleasable
}

