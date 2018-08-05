package io.sellmair.kompass.internal

import io.sellmair.kompass.KompassViewDetour
import kotlin.reflect.KClass

internal interface ViewDetourRegistry {
    fun <Destination : Any, Current : Any, Next : Any> add(
        detour: KompassViewDetour<Destination, Current, Next>,
        destinationClass: KClass<Destination>,
        currentClass: KClass<Current>,
        nextClass: KClass<Next>)

    fun <Destination : Any, Current : Any, Next : Any> findViewDetour(
        destinationClass: KClass<Destination>,
        currentClass: KClass<Destination>,
        nextClass: KClass<Destination>):
        KompassViewDetour<Destination, Current, Next>?
}

