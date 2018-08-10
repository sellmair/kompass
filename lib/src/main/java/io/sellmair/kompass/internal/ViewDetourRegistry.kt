package io.sellmair.kompass.internal

import io.sellmair.kompass.KompassViewAnimation
import io.sellmair.kompass.KompassViewDetour
import kotlin.reflect.KClass

internal interface ViewDetourRegistry {
    fun <Destination : Any, Current : Any, Next : Any> add(
        detour: KompassViewDetour<Destination, Current, Next>,
        destinationClass: KClass<Destination>,
        currentClass: KClass<Current>,
        nextClass: KClass<Next>)

    fun <Destination : Any, Current : Any, Next : Any> findViewDetour(
        destinationClass: KClass<out Destination>,
        currentClass: KClass<out Current>,
        nextClass: KClass<out Next>):
        KompassViewDetour<Destination, Current, Next>?

    fun <Destination : Any, Current : Any, Next : Any>
        setupViewDetour(
        destination: Destination,
        current: Current,
        next: Next): KompassViewAnimation? {
        val detour = findViewDetour(
            destinationClass = destination::class,
            currentClass = current::class,
            nextClass = next::class)

        return detour?.setup(destination, current, next)
    }
}

internal inline fun <reified Destination : Any, reified Current : Any, reified Next : Any>
    ViewDetourRegistry.add(detour: KompassViewDetour<Destination, Current, Next>) {
    this.add(detour, Destination::class, Current::class, Next::class)
}

internal inline fun <reified Destination : Any, reified Current : Any, reified Next : Any>
    ViewDetourRegistry.findViewDetour(): KompassViewDetour<Destination, Current, Next>? {
    return this.findViewDetour(Destination::class, Current::class, Next::class)
}
