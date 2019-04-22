package io.sellmair.kompass.android.fragment

import io.sellmair.kompass.android.GetRouteSyntax
import io.sellmair.kompass.android.fragment.setup.FragmentExtensions
import io.sellmair.kompass.android.fragment.setup.expectThisToBeAFragment
import io.sellmair.kompass.core.Route
import kotlin.reflect.KClass

interface FragmentGetRouteSyntax : GetRouteSyntax,
    FragmentExtensions {
    override val router: FragmentRouter<*>

    override fun <R : Route> getRouteOrNull(clazz: KClass<R>): R? {
        val route = router.fragmentRouteStorage.run { expectThisToBeAFragment().getRouteOrNull() }
        if (clazz.java.isInstance(route)) {
            @Suppress("UNCHECKED_CAST")
            return route as? R
        }

        return null
    }

}