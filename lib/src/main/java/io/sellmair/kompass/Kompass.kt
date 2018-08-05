package io.sellmair.kompass

import android.support.annotation.AnyThread


interface Kompass<Destination> : BackStack {
    @AnyThread
    operator fun get(ship: String): KompassShip<Destination>
}


