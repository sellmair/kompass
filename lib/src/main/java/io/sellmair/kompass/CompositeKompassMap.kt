package io.sellmair.kompass

import io.sellmair.kompass.exception.MissingMapElementException
import io.sellmair.kompass.util.tryOrNull

internal class CompositeKompassMap<in Destination : Any>
(private val maps: List<KompassMap<Destination>>) : KompassMap<Destination> {
    override fun get(destination: Destination): KompassRoute {
        return maps.asSequence()
                .mapNotNull { map -> tryOrNull { map[destination] } }
                .firstOrNull() ?: throw MissingMapElementException(destination)
    }
}