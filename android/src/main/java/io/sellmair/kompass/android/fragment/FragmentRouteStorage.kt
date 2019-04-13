package io.sellmair.kompass.android.fragment

import androidx.fragment.app.Fragment
import io.sellmair.kompass.core.Route

interface FragmentRouteStorage<T : Route> {
    fun Fragment.attach(route: T)
    fun set(fragment: Fragment, route: T) = fragment.attach(route)
    fun getOrNull(fragment: Fragment): T?
    operator fun get(fragment: Fragment): T
}