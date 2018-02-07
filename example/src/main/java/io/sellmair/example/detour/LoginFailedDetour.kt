package io.sellmair.example.detour

import android.annotation.SuppressLint
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.transition.Slide
import android.view.Gravity
import io.sellmair.example.destination.LoginFailedDestination
import io.sellmair.kompass.KompassDetour
import io.sellmair.kompass.annotation.Detour

/**
 * Created by sebastiansellmair on 07.02.18.
 */
@Detour
class LoginFailedDetour
    : KompassDetour<LoginFailedDestination, Fragment, Fragment> {
    @SuppressLint("RtlHardcoded")
    override fun setup(destination: LoginFailedDestination,
                       currentFragment: Fragment,
                       nextFragment: Fragment,
                       transaction: FragmentTransaction) {
        currentFragment.exitTransition = Slide(Gravity.LEFT)
        nextFragment.enterTransition = Slide(Gravity.RIGHT)
        nextFragment.exitTransition = Slide(Gravity.TOP)
        nextFragment.returnTransition = Slide(Gravity.TOP)
    }
}