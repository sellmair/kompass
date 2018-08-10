package io.sellmair.kompass.internal

import io.sellmair.kompass.KompassViewDetour
import kotlin.reflect.KClass

class ViewDetourRegistryImpl : ViewDetourRegistry {

    private val registry = UnsafeRegistry()

    override fun <Destination : Any, Current : Any, Next : Any> add(
        detour: KompassViewDetour<Destination, Current, Next>,
        destinationClass: KClass<Destination>,
        currentClass: KClass<Current>,
        nextClass: KClass<Next>) {
        registry.add(arrayOf(destinationClass, currentClass, nextClass), detour)
    }

    override fun <Destination : Any, Current : Any, Next : Any> findViewDetour(
        destinationClass: KClass<out Destination>,
        currentClass: KClass<out Current>,
        nextClass: KClass<out Next>): KompassViewDetour<Destination, Current, Next>? {
        val detour = registry.find(arrayOf(destinationClass, currentClass, nextClass))
        @Suppress("UNCHECKED_CAST")
        return detour as? KompassViewDetour<Destination, Current, Next>
    }

}


