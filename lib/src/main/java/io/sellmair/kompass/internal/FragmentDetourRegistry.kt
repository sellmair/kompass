package io.sellmair.kompass.internal

import android.support.v4.app.FragmentTransaction
import io.sellmair.kompass.KompassFragmentDetour
import kotlin.reflect.KClass

interface FragmentDetourRegistry {
    fun <Destination : Any, Current : Any, Next : Any> add(
        detour: KompassFragmentDetour<Destination, Current, Next>,
        destinationClass: KClass<Destination>,
        currentClass: KClass<Current>,
        nextClass: KClass<Next>)
}


internal interface SearchableFragmentDetourRegistry : FragmentDetourRegistry {
    fun <Destination : Any, Current : Any, Next : Any> findFragmentDetour(
        destinationClass: KClass<out Destination>,
        currentClass: KClass<out Current>,
        nextClass: KClass<out Next>):
        KompassFragmentDetour<Destination, Current, Next>?
}


internal interface ExecutableFragmentDetourRegistry : SearchableFragmentDetourRegistry {
    fun <Destination : Any, Current : Any, Next : Any>
        setupFragmentDetour(
        destination: Destination,
        current: Current,
        next: Next,
        transaction: FragmentTransaction) {
        val detour = findFragmentDetour(
            destinationClass = destination::class,
            currentClass = current::class,
            nextClass = next::class)

        detour?.setup(destination, current, next, transaction)
    }
}


internal inline fun <reified Destination : Any, reified Current : Any, reified Next : Any>
    FragmentDetourRegistry.add(detour: KompassFragmentDetour<Destination, Current, Next>) {
    this.add(detour, Destination::class, Current::class, Next::class)
}

internal inline fun <reified Destination : Any, reified Current : Any, reified Next : Any>
    SearchableFragmentDetourRegistry.findFragmentDetour():
    KompassFragmentDetour<Destination, Current, Next>? {
    return this.findFragmentDetour(Destination::class, Current::class, Next::class)
}