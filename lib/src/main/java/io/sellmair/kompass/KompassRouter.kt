package io.sellmair.kompass

import android.support.annotation.AnyThread

interface KompassRouter<in Destination> : KeyLessBackStack {

    @AnyThread
    infix fun startAt(destination: Destination)

    @AnyThread
    infix fun navigateTo(destination: Destination)

    @AnyThread
    infix fun beamTo(destination: Destination)

}

