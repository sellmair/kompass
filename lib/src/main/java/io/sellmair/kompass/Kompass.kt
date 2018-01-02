package io.sellmair.kompass

import android.content.Context


/**
 * Created by sebastiansellmair on 06.12.17.
 */
interface Kompass<in Destination : Any> {
    fun getShip(name: String): KompassShip<Destination>
    operator fun get(name: String): KompassShip<Destination>

    fun popBack(): Unit
    fun popBackImmediate(): Boolean
    fun onBack(key: Any? = null, keySingleton: Boolean = true, block: () -> Boolean)
    fun removeFromBackStack(key: Any)

    companion object {
        fun <Destination : Any> create(context: Context,
                                       map: KompassMap<Destination>,
                                       crane: KompassCrane<Destination>)
                : Kompass<Destination> = BaseKompass(context, map, crane)
    }
}