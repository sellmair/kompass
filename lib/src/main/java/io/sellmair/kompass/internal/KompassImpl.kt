package io.sellmair.kompass.internal

import io.sellmair.kompass.*
import io.sellmair.kompass.internal.util.curry

internal class KompassImpl<Destination>(
    override val map: KompassMap<Destination>,
    override val crane: KompassCrane<Destination>,
    override val detour: KompassDetour<Destination>) : Kompass<Destination>,
    KompassContext<Destination>,
    BackStack by BackStackImpl() {

    private val ships = mutableMapOf<String, KompassShip<Destination>>()

    override fun get(ship: String): KompassShip<Destination> {
        return synchronized(this) {
            ships.getOrPut(ship, this::createShip.curry(ship))
        }
    }

    private fun createShip(name: String): KompassShip<Destination> {
        return ShipImpl(
            name = name,
            backStack = this,
            map = this.map,
            crane = this.crane,
            detour = this.detour)
    }
}