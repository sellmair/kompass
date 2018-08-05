package io.sellmair.kompass

interface KompassMap<in Destination> {
    operator fun get(destination: Destination): KompassRoute?
}