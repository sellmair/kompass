package io.sellmair.kompass.android.fragment

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import io.sellmair.kompass.core.Route

interface GenericFragmentTransition<
        in ExitFragment : Fragment, in ExitRoute : Route,
        in EnterFragment : Fragment, in EnterRoute : Route> {
    fun setup(
        transaction: FragmentTransaction,
        exitFragment: ExitFragment, exitRoute: ExitRoute,
        enterFragment: EnterFragment, enterRoute: EnterRoute
    )
}


