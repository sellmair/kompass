package io.sellmair.kompass.internal

import io.sellmair.kompass.KompassFragmentDetour
import kotlin.reflect.KClass

class FragmentDetourRegistryImpl : FragmentDetourRegistry {
    private val registry = UnsafeRegistry()

    override fun <Destination : Any, Current : Any, Next : Any> add(
        detour: KompassFragmentDetour<Destination, Current, Next>,
        destinationClass: KClass<Destination>,
        currentClass: KClass<Current>,
        nextClass: KClass<Next>) {
        registry.add(arrayOf(destinationClass, currentClass, nextClass), detour)
    }

    override fun <Destination : Any, Current : Any, Next : Any> findFragmentDetour(
        destinationClass: KClass<out Destination>,
        currentClass: KClass<out Current>,
        nextClass: KClass<out Next>): KompassFragmentDetour<Destination, Current, Next>? {
        val detour = registry.find(arrayOf(destinationClass, currentClass, nextClass))
        @Suppress("UNCHECKED_CAST")
        return detour as? KompassFragmentDetour<Destination, Current, Next>
    }

}