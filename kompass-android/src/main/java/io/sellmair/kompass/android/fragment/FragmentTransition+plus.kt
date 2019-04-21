package io.sellmair.kompass.android.fragment

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import io.sellmair.kompass.core.Route

operator fun FragmentTransition.plus(other: FragmentTransition): FragmentTransition {
    return CompositeFragmentTransition(this, other)
}

private class CompositeFragmentTransition(
    private val first: FragmentTransition,
    private val second: FragmentTransition
) : FragmentTransition {
    override fun setup(
        transaction: FragmentTransaction,
        exitFragment: Fragment, exitRoute: Route, enterFragment: Fragment, enterRoute: Route
    ) {
        first.setup(transaction, exitFragment, exitRoute, enterFragment, enterRoute)
        second.setup(transaction, exitFragment, exitRoute, enterFragment, enterRoute)
    }
}