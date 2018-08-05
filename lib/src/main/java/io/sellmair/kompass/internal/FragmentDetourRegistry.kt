package io.sellmair.kompass.internal

import io.sellmair.kompass.KompassFragmentDetour
import kotlin.reflect.KClass

internal interface FragmentDetourRegistry {
    fun <Destination : Any, Current : Any, Next : Any> add(
        detour: KompassFragmentDetour<Destination, Current, Next>,
        destinationClass: KClass<Destination>,
        currentClass: KClass<Current>,
        nextClass: KClass<Next>)

    fun <Destination : Any, Current : Any, Next : Any> findFragmentDetour(
        destinationClass: KClass<Destination>,
        currentClass: KClass<Destination>,
        nextClass: KClass<Destination>):
        KompassFragmentDetour<Destination, Current, Next>?
}