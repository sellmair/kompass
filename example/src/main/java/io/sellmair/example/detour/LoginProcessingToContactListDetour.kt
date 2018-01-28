package io.sellmair.example.detour

import android.os.Build
import android.support.v4.app.FragmentTransaction
import android.transition.Slide
import android.view.Gravity
import io.sellmair.example.fragment.ContactListFragment
import io.sellmair.example.fragment.LoginProcessingFragment
import io.sellmair.kompass.KompassDetour
import io.sellmair.kompass.annotation.Detour

@Detour
class LoginProcessingToContactListDetour
    : KompassDetour<Any, LoginProcessingFragment, ContactListFragment> {
    override fun setup(destination: Any,
                       currentFragment: LoginProcessingFragment,
                       nextFragment: ContactListFragment,
                       transaction: FragmentTransaction) {
        if (Build.VERSION.SDK_INT > 21) {
            currentFragment.exitTransition = Slide(Gravity.BOTTOM)
            nextFragment.enterTransition = Slide(Gravity.TOP)
        }
    }
}