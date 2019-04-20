package io.sellmair.kompass.android.fragment

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import io.sellmair.kompass.android.KompassTarget
import io.sellmair.kompass.android.fragment.setup.FragmentFragmentRouterSetupSyntax
import io.sellmair.kompass.core.Route
import io.sellmair.kompass.core.exception.KompassException
import kotlin.reflect.KClass


interface KompassFragment : KompassTarget {

    override val router: FragmentRouter<*>

    override fun <R : Route> getRouteOrNull(clazz: KClass<R>): R? {
        val route = router.fragmentRouteStorage.getOrNull(expectThisToBeAFragment())
        if (clazz.java.isInstance(route)) {
            @Suppress("UNCHECKED_CAST")
            return route as? R
        }

        return null
    }


    fun FragmentRouter<*>.setup(
        savedInstanceState: Bundle?,
        @IdRes containerId: Int, fragmentManager: FragmentManager = expectThisToBeAFragment().childFragmentManager
    ) {
        FragmentFragmentRouterSetupSyntax(expectThisToBeAFragment()).run {
            setup(savedInstanceState, containerId, fragmentManager)
        }
    }


    /* KEEP-87 */
    private fun expectThisToBeAFragment() = this as? Fragment ?: throw KompassException(
        "${KompassFragment::class.java.simpleName} only works for Fragments"
    )

}


