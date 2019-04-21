package io.sellmair.kompass.android.fragment

import io.sellmair.kompass.core.RoutingStack

interface FragmentStackPatcher {
    operator fun invoke(
        transition: FragmentTransition,
        container: FragmentContainer,
        oldStack: RoutingStack<*>,
        newStack: FragmentRoutingStack<*>
    )
}