package io.sellmair.kompass.android

import io.sellmair.kompass.core.Route
import io.sellmair.kompass.core.Router
import io.sellmair.kompass.core.exception.MissingRouteException
import kotlin.reflect.KClass

interface GetRouteSyntax {

    val router: Router<*>

    fun <R : Route> getRouteOrNull(clazz: KClass<R>): R?

    fun <R : Route> getRoute(clazz: KClass<R>): R {
        return getRouteOrNull(clazz) ?: throw MissingRouteException(
            "Route ${clazz.java.simpleName} missing from $this"
        )
    }

}

inline fun <reified T : Route> GetRouteSyntax.getRoute(): T {
    return getRoute(T::class)
}

inline fun <reified T : Route> GetRouteSyntax.getRouteOrNull(): T? {
    return getRouteOrNull(T::class)
}

inline fun <reified T : Route> GetRouteSyntax.route() = lazy { getRoute(T::class) }

