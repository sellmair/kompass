package io.sellmair.kompass.android.example.transitions

import android.transition.Slide
import android.view.Gravity
import androidx.fragment.app.FragmentTransaction
import io.sellmair.kompass.android.example.fragment.ContactListFragment
import io.sellmair.kompass.android.example.fragment.LoginProcessingFragment
import io.sellmair.kompass.android.fragment.GenericFragmentTransition
import io.sellmair.kompass.core.Route

class LoginProcessingToContactListTransition :
    GenericFragmentTransition<LoginProcessingFragment, Route, ContactListFragment, Route> {
    override fun setup(
        transaction: FragmentTransaction,
        exitFragment: LoginProcessingFragment, exitRoute: Route,
        enterFragment: ContactListFragment, enterRoute: Route
    ) {
        exitFragment.exitTransition = Slide(Gravity.BOTTOM)
        enterFragment.enterTransition = Slide(Gravity.TOP).apply {
            startDelay = TransitionsConfiguration.ENTER_START_DELAY
        }
    }
}
