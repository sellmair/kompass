package io.sellmair.kompass.android.fragment.dsl

import androidx.lifecycle.Lifecycle
import io.sellmair.kompass.android.fragment.internal.FragmentContainerLifecycle
import io.sellmair.kompass.android.fragment.internal.GenericFragmentContainerLifecycle

@FragmentRouterDsl
class GenericFragmentContainerLifecycleBuilder {

    @FragmentRouterDsl
    var attachOn: Lifecycle.Event = Lifecycle.Event.ON_RESUME

    @FragmentRouterDsl
    var detachOn: Lifecycle.Event = Lifecycle.Event.ON_PAUSE


    internal fun build(): FragmentContainerLifecycle.Factory =
        GenericFragmentContainerLifecycle.Factory(
            attachEvent = attachOn,
            detachEvent = detachOn
        )
}