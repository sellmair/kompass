package io.sellmair.kompass.android.fragment.internal

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import io.sellmair.kompass.android.fragment.FragmentTransition
import io.sellmair.kompass.core.Route

internal object EmptyFragmentTransition : FragmentTransition {
    override fun setup(
        transaction: FragmentTransaction,
        exitFragment: Fragment, exitRoute: Route, enterFragment: Fragment, enterRoute: Route
    ) = Unit
}