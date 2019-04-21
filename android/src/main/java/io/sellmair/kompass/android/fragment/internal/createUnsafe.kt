package io.sellmair.kompass.android.fragment.internal

import android.os.Parcelable
import io.sellmair.kompass.android.fragment.FragmentRouteStorage
import io.sellmair.kompass.android.fragment.FragmentRoutingStackBundler
import io.sellmair.kompass.android.fragment.ParcelableFragmentRouteStorage
import io.sellmair.kompass.android.fragment.ParcelableFragmentRoutingStackBundler
import io.sellmair.kompass.core.Route

internal fun <T : Route> ParcelableFragmentRouteStorage.Companion.createUnsafe(): FragmentRouteStorage<T> {
    @Suppress("UNCHECKED_CAST")
    return ParcelableFragmentRouteStorage<ParcelableRoute>() as FragmentRouteStorage<T>
}


internal fun <T : Route> ParcelableFragmentRoutingStackBundler.Companion.createUnsafe(
    key: String = defaultKey
): FragmentRoutingStackBundler<T> {
    @Suppress("UNCHECKED_CAST")
    return ParcelableFragmentRoutingStackBundler<ParcelableRoute>(key) as FragmentRoutingStackBundler<T>
}


/**
 * Just a private interface to trick the compiler :)
 */
private interface ParcelableRoute : Route, Parcelable

