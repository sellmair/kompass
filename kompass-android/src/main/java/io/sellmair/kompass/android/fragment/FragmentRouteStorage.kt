package io.sellmair.kompass.android.fragment

import androidx.fragment.app.Fragment
import io.sellmair.kompass.core.Route

/**
 * # FragmentRouteStorage
 * Defines a way of attaching a route to a fragment in a way, that this fragment can retrieve this router later.
 *
 * ## Usage
 * ```
 * fragmentRouteStorage.run { fragment.attach(route) } 
 * val route = fragmentRouteStorage[route]
 * ```
 *
 * @see FragmentGetRouteSyntax
 */
interface FragmentRouteStorage<T : Route> {
    fun Fragment.attach(route: T)
    fun getOrNull(fragment: Fragment): T?
    operator fun get(fragment: Fragment): T
}