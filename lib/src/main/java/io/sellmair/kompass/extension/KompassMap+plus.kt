package io.sellmair.kompass.extension

import io.sellmair.kompass.KompassMap
import io.sellmair.kompass.KompassRoute

operator fun <Destination> KompassMap<Destination>.plus(other: KompassMap<Destination>):
    KompassMap<Destination> = KompassMapConnector(this, other)

private class KompassMapConnector<Destination>(
    private val first: KompassMap<Destination>,
    private val second: KompassMap<Destination>) :
    KompassMap<Destination> {
    override fun get(destination: Destination): KompassRoute? {
        return this.first[destination] ?: second[destination]
    }

}