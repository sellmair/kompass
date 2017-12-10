package io.sellmair.kompass

/**
 * Created by sebastiansellmair on 06.12.17.
 */
interface KompassMap<in Scene> {
    operator fun get(scene: Scene): KompassRoute
}