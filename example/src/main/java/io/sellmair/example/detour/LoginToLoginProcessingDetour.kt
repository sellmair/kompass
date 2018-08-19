package io.sellmair.example.detour

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.transition.Slide
import android.view.Gravity
import io.sellmair.example.fragment.LoginProcessingFragment
import io.sellmair.kompass.KompassFragmentDetour
import io.sellmair.kompass.annotation.Detour


/**
 * Created by sebastiansellmair on 28.01.18.
 */
@Detour
class LoginToLoginProcessingDetour :
    KompassFragmentDetour<Any, Fragment, LoginProcessingFragment>() {
    override fun setup(destination: Any,
                       currentFragment: Fragment,
                       nextFragment: LoginProcessingFragment,
                       transaction: FragmentTransaction) {

        currentFragment.exitTransition = Slide(Gravity.BOTTOM)

        currentFragment.reenterTransition = Slide(Gravity.BOTTOM).apply {
            startDelay = Detours.ENTER_START_DELAY
        }

        nextFragment.enterTransition = Slide(Gravity.TOP).apply {
            startDelay = Detours.ENTER_START_DELAY
        }

        nextFragment.returnTransition = Slide(Gravity.TOP)

    }
}


