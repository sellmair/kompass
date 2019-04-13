package io.sellmair.kompass.android.example.transitions

import android.view.Gravity
import androidx.fragment.app.FragmentTransaction
import androidx.transition.Slide
import io.sellmair.kompass.android.example.fragment.LoginFragment
import io.sellmair.kompass.android.example.fragment.LoginProcessingFragment
import io.sellmair.kompass.android.fragment.GenericFragmentTransition
import io.sellmair.kompass.core.Route


/**
 * Created by sebastiansellmair on 28.01.18.
 */
class LoginToLoginProcessingTransition :
    GenericFragmentTransition<LoginFragment, Route, LoginProcessingFragment, Route> {
    override fun setup(
        transaction: FragmentTransaction,
        exitFragment: LoginFragment, exitRoute: Route,
        enterFragment: LoginProcessingFragment, enterRoute: Route
    ) {
        exitFragment.exitTransition = Slide(Gravity.BOTTOM)
        enterFragment.enterTransition = Slide(Gravity.TOP)
    }

}



