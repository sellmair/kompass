package io.sellmair.kompass.android.fragment

import android.os.Bundle
import android.os.Parcelable
import io.sellmair.kompass.android.ParcelableRoutingStack
import io.sellmair.kompass.android.parcelable
import io.sellmair.kompass.android.utils.log
import io.sellmair.kompass.core.Route
import io.sellmair.kompass.core.RoutingStack
import io.sellmair.kompass.core.routes

class ParcelableFragmentRoutingStackBundler<T>(
    private val key: String = defaultKey
) : FragmentRoutingStackBundleSyntax<T> where T : Route, T : Parcelable {

    override fun RoutingStack<T>.saveTo(outState: Bundle) {
        log("saving routes: ${this.routes.joinToString(", ")}")
        outState.putParcelable(key, parcelable())
    }

    override fun Bundle.restore(): RoutingStack<T>? {
        return getParcelable<ParcelableRoutingStack<T>>(key).also {
            log("restored routes: ${it?.routes?.joinToString(", ")}")
        }
    }


    companion object {
        const val defaultKey = "Kompass: RoutingStack"
    }
}