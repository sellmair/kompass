package io.sellmair.kompass

/**
 * Created by sebastiansellmair on 06.12.17.
 */
interface KompassMap<in Destination> {
    operator fun get(destination: Destination): KompassRoute
}