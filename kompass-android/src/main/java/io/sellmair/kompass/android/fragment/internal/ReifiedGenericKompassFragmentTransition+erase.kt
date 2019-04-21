package io.sellmair.kompass.android.fragment.internal

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import io.sellmair.kompass.android.fragment.GenericFragmentTransition
import io.sellmair.kompass.core.Route

@PublishedApi
internal fun <ExitFragment : Fragment, ExitRoute : Route, EnterFragment : Fragment, EnterRoute : Route>
        ReifiedGenericFragmentTransition<ExitFragment, ExitRoute, EnterFragment, EnterRoute>.erased():
        GenericFragmentTransition<Fragment, Route, Fragment, Route> {
    return ErasedFragmentTransition(this)
}

private class ErasedFragmentTransition<
        ExitFragment : Fragment, ExitRoute : Route, EnterFragment : Fragment, EnterRoute : Route>(
    private val transition: ReifiedGenericFragmentTransition<ExitFragment, ExitRoute, EnterFragment, EnterRoute>
) :
    GenericFragmentTransition<Fragment, Route, Fragment, Route> {
    override fun setup(
        transaction: FragmentTransaction,
        exitFragment: Fragment, exitRoute: Route, enterFragment: Fragment, enterRoute: Route
    ) {
        if (transition.enterFragment.java.isInstance(enterFragment) &&
            transition.enterRoute.java.isInstance(enterRoute) &&
            transition.exitFragment.java.isInstance(exitFragment) &&
            transition.exitRoute.java.isInstance(exitRoute)
        ) {
            @Suppress("UNCHECKED_CAST")
            transition.setup(
                transaction = transaction,
                enterFragment = enterFragment as EnterFragment,
                enterRoute = enterRoute as EnterRoute,
                exitFragment = exitFragment as ExitFragment,
                exitRoute = exitRoute as ExitRoute
            )
        }
    }

}