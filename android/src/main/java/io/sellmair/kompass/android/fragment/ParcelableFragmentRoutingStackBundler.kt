package io.sellmair.kompass.android.fragment

import android.os.Bundle
import android.os.Parcelable
import io.sellmair.kompass.android.ParcelableRoutingStack
import io.sellmair.kompass.android.parcelable
import io.sellmair.kompass.core.Route
import io.sellmair.kompass.core.RoutingStack

class ParcelableFragmentRoutingStackBundler<T>(
    private val key: String = defaultKey
) : FragmentRoutingStackBundler<T> where T : Route, T : Parcelable {

    override fun RoutingStack<T>.saveTo(outState: Bundle) {
        outState.putParcelable(key, parcelable())
    }

    override fun Bundle.restore(): RoutingStack<T>? {
        return getParcelable<ParcelableRoutingStack<T>>(key)
    }


    companion object {
        const val defaultKey = "Kompass: RoutingStack"
    }
}