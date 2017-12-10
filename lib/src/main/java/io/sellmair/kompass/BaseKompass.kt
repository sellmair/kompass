package io.sellmair.kompass

import android.os.Bundle
import io.sellmair.kompass.util.requireMainThread
import kotlin.reflect.KClass

/**
 * Created by sebastiansellmair on 06.12.17.
 */
internal open class BaseKompass<in Destination :
KompassDestination>(private val map: KompassMap<Destination>) : Kompass<Destination> {

    private val routeQueue = RouteQueue()

    override fun setShip(ship: KompassShip) {
        routeQueue.ship = ship
    }


    override final fun <T : Destination> navigateTo(destination: T, replaceCurrent: Boolean) {
        requireMainThread()
        if (replaceCurrent) routeQueue.clear()
    }

    override fun <T : Destination> getDestination(clazz: KClass<T>, bundle: Bundle): T {
        TODO()
    }

    override fun exit() {
        requireMainThread()
    }


}