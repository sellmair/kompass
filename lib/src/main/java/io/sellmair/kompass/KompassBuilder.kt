package io.sellmair.kompass

import android.support.annotation.UiThread
import io.sellmair.kompass.extension.plus
import io.sellmair.kompass.internal.DetourRegistry
import io.sellmair.kompass.internal.DetourRegistryImpl
import io.sellmair.kompass.internal.ExecutableDetourRegistry
import io.sellmair.kompass.internal.KompassImpl
import io.sellmair.kompass.internal.precondition.Precondition
import io.sellmair.kompass.internal.precondition.requireMainThread

/*
################################################################################################
PUBLIC API
################################################################################################
*/

interface KompassBuilder<Destination> : DetourRegistry {
    @UiThread
    fun addCrane(crane: KompassCrane<Destination>): KompassBuilder<Destination>

    @UiThread
    fun addMap(map: KompassMap<Destination>): KompassBuilder<Destination>

    @UiThread
    fun build(): Kompass<Destination>

    companion object
}

/*
################################################################################################
INTERNAL FACTORY
################################################################################################
*/
internal operator fun <Destination : Any>
    KompassBuilder.Companion.invoke(): KompassBuilder<Destination> {
    return KompassBuilderImpl()
}


/*
################################################################################################
PRIVATE IMPLEMENTATION
################################################################################################
*/

private class KompassBuilderImpl<Destination : Any>(
    private val registry: ExecutableDetourRegistry = DetourRegistryImpl()) :
    KompassBuilder<Destination>,
    DetourRegistry by registry {

    private var crane = KompassCrane.empty<Destination>()
    private var map = KompassMap.empty<Destination>()

    @UiThread
    override fun addCrane(crane: KompassCrane<Destination>): KompassBuilder<Destination> {
        Precondition.requireMainThread()
        this.crane += crane
        return this
    }

    @UiThread
    override fun addMap(map: KompassMap<Destination>): KompassBuilder<Destination> {
        Precondition.requireMainThread()
        this.map += map
        return this
    }

    @UiThread
    override fun build(): Kompass<Destination> {
        Precondition.requireMainThread()
        return KompassImpl(
            map = map,
            crane = crane,
            registry = registry
        )
    }

}