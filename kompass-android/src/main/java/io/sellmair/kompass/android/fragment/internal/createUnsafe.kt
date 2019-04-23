package io.sellmair.kompass.android.fragment.internal

import android.os.Parcelable
import io.sellmair.kompass.android.fragment.FragmentRouteStorageSyntax
import io.sellmair.kompass.android.fragment.FragmentRoutingStackBundleSyntax
import io.sellmair.kompass.android.fragment.ParcelableFragmentRouteStorageSyntax
import io.sellmair.kompass.android.fragment.ParcelableFragmentRoutingStackBundleSyntax
import io.sellmair.kompass.core.Route

internal fun <T : Route> ParcelableFragmentRouteStorageSyntax.Companion.createUnsafe(): FragmentRouteStorageSyntax<T> {
    @Suppress("UNCHECKED_CAST")
    return ParcelableFragmentRouteStorageSyntax<ParcelableRoute>() as FragmentRouteStorageSyntax<T>
}


internal fun <T : Route> ParcelableFragmentRoutingStackBundleSyntax.Companion.createUnsafe(
    key: String = defaultKey
): FragmentRoutingStackBundleSyntax<T> {
    @Suppress("UNCHECKED_CAST")
    return ParcelableFragmentRoutingStackBundleSyntax<ParcelableRoute>(key) as FragmentRoutingStackBundleSyntax<T>
}


/**
 * Just a private interface to trick the compiler :)
 */
private interface ParcelableRoute : Route, Parcelable

