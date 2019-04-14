package io.sellmair.kompass.android.example.transitions

import android.annotation.SuppressLint
import android.view.Gravity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.transition.Slide
import io.sellmair.kompass.android.example.fragment.ChatFragment
import io.sellmair.kompass.android.example.fragment.ContactListFragment
import io.sellmair.kompass.android.fragment.FragmentTransition
import io.sellmair.kompass.core.Route


class ContactListToChatTransition : FragmentTransition {
    @SuppressLint("RtlHardcoded")
    override fun setup(
        transaction: FragmentTransaction,
        exitFragment: Fragment, exitRoute: Route,
        enterFragment: Fragment, enterRoute: Route
    ) {
        if (exitFragment is ContactListFragment && enterFragment is ChatFragment) {
            exitFragment.exitTransition = Slide(Gravity.LEFT)
            enterFragment.enterTransition = Slide(Gravity.RIGHT)
        }

        if (exitFragment is ChatFragment && enterFragment is ContactListFragment) {
            exitFragment.exitTransition = Slide(Gravity.RIGHT)
            enterFragment.enterTransition = Slide(Gravity.LEFT)
        }
    }
}

