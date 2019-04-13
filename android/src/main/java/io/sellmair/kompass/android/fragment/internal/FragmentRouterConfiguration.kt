package io.sellmair.kompass.android.fragment.internal

import io.sellmair.kompass.android.fragment.FragmentMap
import io.sellmair.kompass.android.fragment.FragmentRouteStorage
import io.sellmair.kompass.core.Route

internal interface FragmentRouterConfiguration<T : Route> {
    val fragmentMap: FragmentMap<T>
    val fragmentRouteStorage: FragmentRouteStorage<T>
}

