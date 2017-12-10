package io.sellmair.kompass

import io.sellmair.kompass.util.requireMainThread

/**
 * Created by sebastiansellmair on 06.12.17.
 */
internal class RouteQueue {

    private val buffer = mutableListOf<KompassRoute>()

    var ship: KompassShip? = null
        set(value) {
            requireMainThread()

            field = value
            if (value == null) return

            buffer.forEach { route ->
                value.navigateTo(route)
            }

            buffer.clear()
        }

    fun clear() {
        requireMainThread()
        buffer.clear()
    }

    fun pushRoute(route: KompassRoute) {
        requireMainThread()
        ship?.navigateTo(route) ?: run {
            buffer.add(route)
        }
    }
}