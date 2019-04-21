package io.sellmair.kompass.android.fragment.internal

import androidx.lifecycle.Lifecycle
import io.sellmair.kompass.android.fragment.FragmentContainer
import io.sellmair.kompass.android.fragment.FragmentRouter

internal interface FragmentContainerLifecycle {

    interface Factory {
        operator fun invoke(router: FragmentRouter<*>): FragmentContainerLifecycle
    }

    fun setup(lifecycle: Lifecycle, container: FragmentContainer)

}