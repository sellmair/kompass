package io.sellmair.example.detour

import android.support.v4.app.FragmentTransaction
import android.transition.Slide
import android.view.Gravity
import io.sellmair.example.fragment.ChatFragment
import io.sellmair.example.fragment.ContactListFragment
import io.sellmair.kompass.KompassDetour
import io.sellmair.kompass.annotation.Detour

/**
 * Created by sebastiansellmair on 28.01.18.
 */
@Detour
class ContactListToChatDetour : KompassDetour<Any, ContactListFragment, ChatFragment> {
    override fun setup(destination: Any,
                       currentFragment: ContactListFragment,
                       nextFragment: ChatFragment,
                       transaction: FragmentTransaction) {
        currentFragment.exitTransition = Slide(Gravity.LEFT)
        nextFragment.enterTransition = Slide(Gravity.RIGHT)
    }

}