package io.sellmair.kompass.android.fragment

import androidx.fragment.app.Fragment
import io.sellmair.kompass.core.Route
import kotlin.reflect.KClass

interface FragmentMap<in T : Route> {
    operator fun get(route: T): KClass<out Fragment>?
}