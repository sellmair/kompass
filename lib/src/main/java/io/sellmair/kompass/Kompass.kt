package io.sellmair.kompass

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import io.sellmair.kompass.exception.MissingBundleSerializerException
import io.sellmair.kompass.exception.MissingMapElementException
import io.sellmair.kompass.util.tryOrNull


/**
 * Created by sebastiansellmair on 06.12.17.
 */
interface Kompass<in Destination : Any> {
    fun getShip(name: String): KompassShip<Destination>
    operator fun get(name: String): KompassShip<Destination>
    fun popBack()
    fun popBackImmediate(): Boolean
    fun onBack(key: Any? = null, keySingleton: Boolean = true, block: () -> Boolean)
    fun removeFromBackStack(key: Any)

    companion object {
        fun <Destination : Any> create(context: Context,
                                       map: KompassMap<Destination>,
                                       crane: KompassCrane<Destination>,
                                       pilot: KompassDetourPilot)
                : Kompass<Destination> = BaseKompass(context, map, crane, pilot)

        fun <Destination : Any> builder(context: Context) = KompassBuilder<Destination>(context)
    }
}

class KompassBuilder<Destination : Any> internal constructor(private val context: Context) {
    private val maps = mutableListOf<KompassMap<Destination>>()
    private val cranes = mutableListOf<KompassCrane<Destination>>()
    private val pilots = mutableListOf<KompassDetourPilot>()

    @PublishedApi
    internal val detourPilot = KompassDetourPilot.create()

    fun addMap(vararg maps: KompassMap<Destination>) {
        this.maps.addAll(maps)
    }

    fun addCrane(vararg cranes: KompassCrane<Destination>) {
        this.cranes.addAll(cranes)
    }

    fun addPilot(vararg pilots: KompassDetourPilot) {
        this.pilots.addAll(pilots)
    }

    inline fun <reified Destination : Any,
            reified CurrentFragment : Fragment,
            reified NextFragment : Fragment>
            addDetour(detour: KompassDetour<Destination, CurrentFragment, NextFragment>) {
        this.detourPilot.registerDetour(detour)
    }

    fun build(): Kompass<Destination>
            = Kompass.create(context, CompositeKompassMap(maps),
            CompositeKompassCrane(cranes), CompositeKompassDetourPilot(pilots))

    init {
        pilots.add(detourPilot)
    }
}

internal class CompositeKompassMap<Destination : Any>(private val maps: List<KompassMap<Destination>>)
    : KompassMap<Destination> {
    override fun get(destination: Destination): KompassRoute {
        return maps.asSequence()
                .mapNotNull { map -> tryOrNull { map[destination] } }
                .firstOrNull() ?: throw MissingMapElementException(destination)
    }
}

internal class CompositeKompassCrane<Destination : Any>(private val cranes: List<KompassCrane<Destination>>)
    : KompassCrane<Destination> {
    override fun bundle(destination: Destination): Bundle {
        return cranes.asSequence()
                .mapNotNull { crane -> tryOrNull { crane.bundle(destination) } }
                .firstOrNull() ?: throw MissingBundleSerializerException(destination)
    }

}

internal class CompositeKompassDetourPilot(private val pilotes: List<KompassDetourPilot>) : OpenDetourPilot() {
    override fun setup(destination: Any, currentFragment: Fragment, nextFragment: Fragment, transaction: FragmentTransaction) {
        super.setup(destination, currentFragment, nextFragment, transaction)
        pilotes.forEach { pilot ->
            pilot.setup(destination, currentFragment, nextFragment, transaction)
        }
    }
}