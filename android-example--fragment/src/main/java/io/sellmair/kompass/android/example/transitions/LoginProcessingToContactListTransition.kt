package io.sellmair.kompass.android.example.transitions

import android.view.Gravity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.transition.Slide
import io.sellmair.kompass.android.example.fragment.ContactListFragment
import io.sellmair.kompass.android.example.fragment.LoginProcessingFragment
import io.sellmair.kompass.android.fragment.FragmentTransition
import io.sellmair.kompass.core.Route

class LoginProcessingToContactListTransition : FragmentTransition {
    override fun setup(
        transaction: FragmentTransaction,
        exitFragment: Fragment, exitRoute: Route,
        enterFragment: Fragment, enterRoute: Route
    ) {
        if (exitFragment is LoginProcessingFragment && enterFragment is ContactListFragment) {
            exitFragment.exitTransition = Slide(Gravity.BOTTOM)
            enterFragment.enterTransition = Slide(Gravity.TOP).apply {
                startDelay = TransitionsConfiguration.ENTER_START_DELAY
            }
        }
    }
}
