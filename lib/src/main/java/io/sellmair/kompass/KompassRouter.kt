package io.sellmair.kompass

import android.support.annotation.AnyThread

interface KompassRouter<in Destination> : KeyLessBackStack {

    @AnyThread
    fun startAt(destination: Destination)

    @AnyThread
    fun navigateTo(destination: Destination)

    @AnyThread
    fun beamTo(destination: Destination)

}

