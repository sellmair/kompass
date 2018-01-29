package io.sellmair.kompass

import android.content.Context
import android.support.v4.app.Fragment

class KompassBuilder<Destination : Any> internal constructor(private val context: Context) {
    private val maps = mutableListOf<KompassMap<Destination>>()
    private val cranes = mutableListOf<KompassCrane<Destination>>()
    private val pilots = mutableListOf<KompassDetourPilot>()

    @PublishedApi
    internal val detourPilot = KompassDetourPilot.create()

    fun addMap(vararg maps: KompassMap<Destination>): KompassBuilder<Destination> {
        this.maps.addAll(maps)
        return this
    }

    fun addCrane(vararg cranes: KompassCrane<Destination>): KompassBuilder<Destination> {
        this.cranes.addAll(cranes)
        return this
    }

    fun addPilot(vararg pilots: KompassDetourPilot): KompassBuilder<Destination> {
        this.pilots.addAll(pilots)
        return this
    }

    inline fun <reified Destination : Any,
            reified CurrentFragment : Fragment,
            reified NextFragment : Fragment>
            addDetour(detour: KompassDetour<Destination, CurrentFragment, NextFragment>) {
        this.detourPilot.registerDetour(detour)
    }

    fun build(): Kompass<Destination> = Kompass.create(context, CompositeKompassMap(maps),
            CompositeKompassCrane(cranes), CompositeKompassDetourPilot(pilots))

    init {
        pilots.add(detourPilot)
    }
}