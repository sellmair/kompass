package io.sellmair.kompass.android.fragment.setup

import androidx.fragment.app.Fragment

class FragmentFragmentRouterSetupSyntax(fragment: Fragment) :
    FragmentRouterSetupSyntax,
    FragmentRouterHost by FragmentFragmentRouterHost(fragment),
    InvokeOnSaveInstanceStateSyntax by FragmentInvokeOnSaveInstanceStateSyntax(fragment)