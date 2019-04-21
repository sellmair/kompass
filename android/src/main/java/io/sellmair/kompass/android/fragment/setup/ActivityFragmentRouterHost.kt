package io.sellmair.kompass.android.fragment.setup

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle

internal class ActivityFragmentRouterHost(override val activity: FragmentActivity) : FragmentRouterHost {
    override val lifecycle: Lifecycle get() = activity.lifecycle
}


