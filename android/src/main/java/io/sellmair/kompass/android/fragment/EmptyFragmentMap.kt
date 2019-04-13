package io.sellmair.kompass.android.fragment

import androidx.fragment.app.Fragment
import io.sellmair.kompass.core.Route
import kotlin.reflect.KClass

class EmptyFragmentMap<T : Route> : FragmentMap<T> {
    override fun get(route: T): KClass<out Fragment>? = null
}