package io.sellmair.kompass.android.fragment.internal

import io.sellmair.kompass.android.fragment.FragmentMap
import io.sellmair.kompass.android.fragment.FragmentRouteStorageSyntax
import io.sellmair.kompass.android.fragment.FragmentRoutingStackBundleSyntax
import io.sellmair.kompass.core.Route

internal interface FragmentRouterConfiguration<T : Route> {
    val fragmentMap: FragmentMap<T>
    val fragmentRouteStorageSyntax: FragmentRouteStorageSyntax<T>
    val fragmentRoutingStackBundleSyntax: FragmentRoutingStackBundleSyntax<T>
}

