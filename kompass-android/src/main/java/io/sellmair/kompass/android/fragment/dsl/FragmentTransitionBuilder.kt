package io.sellmair.kompass.android.fragment.dsl

import androidx.fragment.app.Fragment
import io.sellmair.kompass.android.fragment.FragmentTransition
import io.sellmair.kompass.android.fragment.GenericFragmentTransition
import io.sellmair.kompass.android.fragment.internal.EmptyFragmentTransition
import io.sellmair.kompass.android.fragment.internal.erased
import io.sellmair.kompass.android.fragment.internal.reified
import io.sellmair.kompass.android.fragment.plus
import io.sellmair.kompass.core.Route

@FragmentRouterDsl
class FragmentTransitionBuilder {

    private var transition: FragmentTransition = EmptyFragmentTransition

    @FragmentRouterDsl
    fun register(transition: FragmentTransition) {
        this.transition += transition
    }

    @JvmName("registerGeneric")
    @FragmentRouterDsl
    inline fun <reified ExitFragment : Fragment, reified ExitRoute : Route,
            reified EnterFragment : Fragment, reified EnterRoute : Route> register(
        transition: GenericFragmentTransition<ExitFragment, ExitRoute, EnterFragment, EnterRoute>
    ) = register(transition.reified().erased())


    internal fun build(): FragmentTransition {
        return transition
    }
}