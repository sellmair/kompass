package io.sellmair.kompass.android.example.transitions

import android.transition.Slide
import android.view.Gravity
import androidx.fragment.app.FragmentTransaction
import io.sellmair.kompass.android.example.fragment.ChatFragment
import io.sellmair.kompass.android.example.fragment.ContactListFragment
import io.sellmair.kompass.android.fragment.GenericFragmentTransition
import io.sellmair.kompass.core.Route


class ContactListToChatTransition : GenericFragmentTransition<ContactListFragment, Route, ChatFragment, Route> {
    override fun setup(
        transaction: FragmentTransaction,
        exitFragment: ContactListFragment, exitRoute: Route,
        enterFragment: ChatFragment, enterRoute: Route
    ) {
        exitFragment.exitTransition = Slide(Gravity.LEFT)
        enterFragment.enterTransition = Slide(Gravity.RIGHT)
    }
}