package io.sellmair.kompass

import android.support.annotation.AnyThread


/*
################################################################################################
PUBLIC API
################################################################################################
*/

interface Kompass<Destination> : BackStack {
    @AnyThread
    operator fun get(ship: String): KompassShip<Destination>

    companion object
}


/*
################################################################################################
PUBLIC BUILDER FUNCTION
################################################################################################
*/

fun <Destination : Any> Kompass.Companion.builder(): KompassBuilder<Destination> {
    return KompassBuilder()
}