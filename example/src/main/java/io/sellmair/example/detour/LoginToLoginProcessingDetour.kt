package io.sellmair.example.detour

import android.os.Build
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.transition.Slide
import android.view.Gravity
import io.sellmair.example.fragment.LoginProcessingFragment
import io.sellmair.kompass.KompassDetour
import io.sellmair.kompass.annotation.Detour


/**
 * Created by sebastiansellmair on 28.01.18.
 */
@Detour
class LoginToLoginProcessingDetour : KompassDetour<Any, Fragment, LoginProcessingFragment> {
    override fun setup(destination: Any,
                       currentFragment: Fragment,
                       nextFragment: LoginProcessingFragment,
                       transaction: FragmentTransaction) {
        if (Build.VERSION.SDK_INT > 21) {
            currentFragment.exitTransition = Slide(Gravity.BOTTOM)
            nextFragment.enterTransition = Slide(Gravity.TOP)
        }
    }
}

