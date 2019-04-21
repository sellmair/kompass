package io.sellmair.kompass.android.fragment

import android.os.Bundle
import io.sellmair.kompass.core.Route
import io.sellmair.kompass.core.RoutingStack

interface FragmentRoutingStackBundler<T : Route> {
    fun RoutingStack<T>.saveTo(outState: Bundle)
    fun Bundle.restore(): RoutingStack<T>?
}


