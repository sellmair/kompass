package io.sellmair.kompass.android.fragment

import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.Fragment
import io.sellmair.kompass.core.Route
import io.sellmair.kompass.core.exception.MissingRouteException

class ParcelableFragmentRouteStorage<T>(
    private val bundleKey: String = DEFAULT_BUNDLE_KEY
) : FragmentRouteStorage<T> where T : Parcelable, T : Route {

    override fun Fragment.attach(route: T) {
        val arguments = this.arguments ?: Bundle()
        arguments.putParcelable(bundleKey, route)
        this.arguments = arguments
    }

    override fun Fragment.getRouteOrNull(): T? {
        return arguments?.getParcelable(bundleKey)
    }

    override fun Fragment.getRoute(): T {
        return getRouteOrNull() ?: throw MissingRouteException(
            "Expected route with key $bundleKey"
        )
    }

    companion object {
        const val DEFAULT_BUNDLE_KEY = "Kompass Route"
    }

}


