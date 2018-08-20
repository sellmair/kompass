@file:Suppress("MemberVisibilityCanBePrivate")

package io.sellmair.kompass

import android.support.annotation.UiThread
import io.sellmair.kompass.extension.plus
import io.sellmair.kompass.internal.*
import io.sellmair.kompass.internal.precondition.Precondition
import io.sellmair.kompass.internal.precondition.requireMainThread

/*
################################################################################################
PUBLIC API
################################################################################################
*/


class KompassBuilder<Destination : Any> internal constructor(
    private val registry: ExecutableDetourRegistry = DetourRegistryImpl()) {

    @PublishedApi
    internal val fragmentDetourRegistry: FragmentDetourRegistry = registry

    @PublishedApi
    internal val viewDetourRegistry: ViewDetourRegistry = registry

    private var crane = KompassCrane.empty<Destination>()

    private var map = KompassMap.empty<Destination>()

    @UiThread
    fun addCrane(crane: KompassCrane<Destination>): KompassBuilder<Destination> = apply {
        Precondition.requireMainThread()
        this.crane += crane
        return this
    }

    @UiThread
    fun addMap(map: KompassMap<Destination>): KompassBuilder<Destination> = apply {
        Precondition.requireMainThread()
        this.map += map
        return this
    }

    @UiThread
    inline fun <
        reified Destination : Any,
        reified CurrentFragment : Any,
        reified NextFragment : Any> addDetour(
        fragmentDetour: KompassFragmentDetour<Destination, CurrentFragment, NextFragment>) = apply {
        fragmentDetourRegistry.add(fragmentDetour,
            destinationClass = Destination::class,
            currentClass = CurrentFragment::class,
            nextClass = NextFragment::class)
    }

    @UiThread
    inline fun <
        reified Destination : Any,
        reified CurrentView : Any,
        reified NextView : Any> addDetour(
        viewDetour: KompassViewDetour<Destination, CurrentView, NextView>) = apply {
        viewDetourRegistry.add(viewDetour,
            destinationClass = Destination::class,
            currentClass = CurrentView::class,
            nextClass = NextView::class)
    }

    @UiThread
    fun build(): Kompass<Destination> {
        Precondition.requireMainThread()
        return KompassImpl(
            map = map,
            crane = crane,
            registry = registry
        )
    }

}