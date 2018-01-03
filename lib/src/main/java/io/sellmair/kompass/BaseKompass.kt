package io.sellmair.kompass

import android.content.Context
import android.content.Intent
import io.sellmair.kompass.annotation.RequiresMainThread
import io.sellmair.kompass.util.mainThread
import io.sellmair.kompass.util.requireMainThread

/**
 * Created by sebastiansellmair on 06.12.17.
 */
internal open class BaseKompass<in Destination : Any>(private val context: Context,
                                                      internal val map: KompassMap<Destination>,
                                                      internal val crane: KompassCrane<Destination>,
                                                      internal val pilot: KompassDetourPilot) : Kompass<Destination> {

    private val harbor: MutableMap<String, KompassShip<Destination>> = mutableMapOf()
    private val backStack = mutableListOf<KompassBack>()

    override fun getShip(name: String): KompassShip<Destination> {
        return harbor[name] ?: run {
            val newShip = BaseShip(this)
            harbor[name] = newShip
            newShip
        }
    }

    override fun get(name: String): KompassShip<Destination> {
        return getShip(name)
    }


    override fun popBack() {
        mainThread {
            popBackImmediate()
        }
    }

    @RequiresMainThread
    override fun popBackImmediate(): Boolean {
        requireMainThread()
        synchronized(this) {
            val lastIndex = backStack.lastIndex
            if (lastIndex < 0) return false
            val garbageBin = mutableListOf<KompassBack>()
            val didSomething = (lastIndex..0).asSequence()
                    .map { index -> backStack[index] }
                    .map { kompassBack -> kompassBack.back().also { garbageBin.add(kompassBack) } }
                    .filter { it }
                    .firstOrNull() ?: false

            backStack.removeAll(garbageBin)
            return didSomething
        }

    }

    override fun onBack(key: Any?, keySingleton: Boolean, block: () -> Boolean) {
        synchronized(this) {
            key?.let { key ->
                this.backStack.removeAll { kompassBack ->
                    kompassBack.key == key && (keySingleton || kompassBack.keySingleton)
                }
            }
            this.backStack.add(BaseKompassBack(key, keySingleton, block))
        }
    }

    override fun removeFromBackStack(key: Any) {
        synchronized(this) {
            this.backStack.removeAll { it.key == key }
        }
    }


    internal fun getCaptain(destination: Destination): Captain {
        val route = map[destination]
        val bundle = if (destination is io.sellmair.kompass.KompassDestination) destination.asBundle()
        else crane.bundle(destination)

        return when (route) {
            is IntentKompassRoute -> IntentCaptain(route.intent, destination)
            is ActivityKompassRoute<*> -> run {
                val intent = Intent(context, route.activityClass.java)
                intent.putExtras(bundle)
                IntentCaptain(intent, destination)
            }
            is FragmentKompassRoute -> run {
                route.fragment.arguments = bundle
                FragmentCaptain(route.fragment, destination)
            }
        }
    }

}