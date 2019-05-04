package io.sellmair.kompass.android.example.transitions

import android.view.Gravity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.transition.Slide
import io.sellmair.kompass.android.example.fragment.LoginFailedFragment
import io.sellmair.kompass.android.example.fragment.LoginFragment
import io.sellmair.kompass.android.fragment.FragmentTransition
import io.sellmair.kompass.core.Route

object LoginFailedToLoginTransition : FragmentTransition {
    override fun setup(
        transaction: FragmentTransaction,
        exitFragment: Fragment, exitRoute: Route,
        enterFragment: Fragment, enterRoute: Route
    ) {
        if (exitFragment is LoginFailedFragment && enterFragment is LoginFragment) {
            exitFragment.exitTransition = Slide(Gravity.TOP)
            enterFragment.enterTransition = Slide(Gravity.BOTTOM).apply {
                startDelay = 300
            }
        }
    }

}