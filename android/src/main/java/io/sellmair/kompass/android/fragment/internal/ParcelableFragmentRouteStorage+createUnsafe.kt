package io.sellmair.kompass.android.fragment.internal

import android.os.Parcelable
import io.sellmair.kompass.android.fragment.FragmentRouteStorage
import io.sellmair.kompass.android.fragment.ParcelableFragmentRouteStorage
import io.sellmair.kompass.core.Route

internal fun <T : Route> ParcelableFragmentRouteStorage.Companion.createUnsafe(): FragmentRouteStorage<T> {
    @Suppress("UNCHECKED_CAST")
    return ParcelableFragmentRouteStorage<ParcelableRoute>() as FragmentRouteStorage<T>
}


/**
 * Just a private interface to trick the compiler :)
 */
private interface ParcelableRoute : Route, Parcelable