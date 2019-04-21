package io.sellmair.kompass.android.fragment.setup

import android.os.Bundle
import androidx.fragment.app.FragmentManager
import io.sellmair.kompass.android.fragment.FragmentContainer
import io.sellmair.kompass.android.fragment.FragmentRouter


internal interface FragmentRouterSetupSyntax : FragmentRouterHost, InvokeOnSaveInstanceStateSyntax {

    fun FragmentRouter<*>.setup(savedInstanceState: Bundle?, containerId: Int, fragmentManager: FragmentManager) {
        invokeOnSaveInstanceState { outState -> saveState(outState) }
        restoreState(savedInstanceState)
        fragmentContainerLifecycle.setup(lifecycle, FragmentContainer(activity, fragmentManager, containerId))
    }

}


