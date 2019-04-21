package io.sellmair.kompass.android.fragment.setup

import androidx.fragment.app.FragmentActivity

class ActivityFragmentRouterSetupSyntax(activity: FragmentActivity) :
    FragmentRouterSetupSyntax,
    FragmentRouterHost by ActivityFragmentRouterHost(activity),
    InvokeOnSaveInstanceStateSyntax by ActivityInvokeOnSaveInstanceStateSyntax(activity)