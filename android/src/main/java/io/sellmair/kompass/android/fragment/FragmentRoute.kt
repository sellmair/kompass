package io.sellmair.kompass.android.fragment

import androidx.fragment.app.Fragment
import io.sellmair.kompass.core.Route
import kotlin.reflect.KClass

interface FragmentRoute : Route {
    val fragment: KClass<out Fragment>
}