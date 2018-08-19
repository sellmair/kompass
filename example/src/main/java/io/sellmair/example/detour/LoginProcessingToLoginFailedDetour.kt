package io.sellmair.example.detour

import android.annotation.SuppressLint
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.transition.Slide
import android.view.Gravity
import io.sellmair.example.destination.LoginFailedDestination
import io.sellmair.kompass.KompassFragmentDetour
import io.sellmair.kompass.annotation.Detour

/**
 * Created by sebastiansellmair on 07.02.18.
 */
@Detour
class LoginProcessingToLoginFailedDetour
    : KompassFragmentDetour<LoginFailedDestination, Fragment, Fragment>() {
    @SuppressLint("RtlHardcoded")
    override fun setup(destination: LoginFailedDestination,
                       currentFragment: Fragment,
                       nextFragment: Fragment,
                       transaction: FragmentTransaction) {

        currentFragment.returnTransition = Slide(Gravity.BOTTOM)

        currentFragment.reenterTransition = Slide(Gravity.BOTTOM).apply {
            startDelay = Detours.ENTER_START_DELAY
        }

        nextFragment.enterTransition = Slide(Gravity.TOP).apply {
            startDelay = Detours.ENTER_START_DELAY
        }

        nextFragment.returnTransition = Slide(Gravity.TOP)
    }
}