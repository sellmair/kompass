package io.sellmair.kompass.android

import io.sellmair.kompass.android.fragment.KompassFragment
import io.sellmair.kompass.core.Route
import io.sellmair.kompass.core.Router
import io.sellmair.kompass.core.exception.MissingRouteException
import kotlin.reflect.KClass

/**
 * # GetRouteSyntax
 * Allows to retrieve the currently associated route from the router.
 * Targets of routes typically implement this syntax.
 *
 * [KompassFragment] implements this syntax which allows the fragment to retrieve the route for which the
 * fragment was created from the router.
 *
 * E.g. Imagine a "HomeRoute" that will display a "HomeFragment".
 *
 * ```
 * class HomeFragment: Fragment(), KompassFragment {
 *
 *    // router that can be retrieved from dependency injection or similar
 *    override val router: FragmentRouter<AppRoute> = ...
 *
 *    // route that can be retrieved using the GetRouteSyntax
 *    private val route: HomeRoute by route()
 *
 *     ...
 * }
 *
 * ```
 */
interface GetRouteSyntax {

    val router: Router<*>

    /**
     * @return the associated route as type of [clazz] if possible.
     * Null if the type does not match, or the route was not found
     */
    fun <R : Route> getRouteOrNull(clazz: KClass<R>): R?


    /**
     * @return the associated route as type of [clazz].
     * @throws MissingRouteException if the route is not the correct type or cannot be found
     */
    fun <R : Route> getRoute(clazz: KClass<R>): R {
        return getRouteOrNull(clazz) ?: throw MissingRouteException(
            "Route ${clazz.java.simpleName} missing from $this"
        )
    }

}


/**
 * @see GetRouteSyntax.getRoute
 */
inline fun <reified T : Route> GetRouteSyntax.getRoute(): T {
    return getRoute(T::class)
}

/**
 * @see GetRouteSyntax.getRouteOrNull
 */
inline fun <reified T : Route> GetRouteSyntax.getRouteOrNull(): T? {
    return getRouteOrNull(T::class)
}


/**
 * Lazy version of [getRoute]
 */
inline fun <reified T : Route> GetRouteSyntax.route() = lazy { getRoute(T::class) }


/**
 * Lazy version of [getRouteOrNull]
 */
inline fun <reified T : Route> GetRouteSyntax.routeOrNull() = lazy { getRouteOrNull(T::class) }

