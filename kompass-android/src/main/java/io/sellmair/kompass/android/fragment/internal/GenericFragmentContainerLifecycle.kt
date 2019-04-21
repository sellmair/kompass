package io.sellmair.kompass.android.fragment.internal

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import io.sellmair.kompass.android.fragment.FragmentContainer
import io.sellmair.kompass.android.fragment.FragmentRouter
import io.sellmair.kompass.android.utils.requireMainThread

internal class GenericFragmentContainerLifecycle(
    private val router: FragmentRouter<*>,
    private val attachEvent: Lifecycle.Event,
    private val detachEvent: Lifecycle.Event
) : FragmentContainerLifecycle {

    data class Factory(
        val attachEvent: Lifecycle.Event,
        val detachEvent: Lifecycle.Event
    ) : FragmentContainerLifecycle.Factory {
        override fun invoke(router: FragmentRouter<*>): FragmentContainerLifecycle {
            return GenericFragmentContainerLifecycle(
                router = router,
                attachEvent = attachEvent,
                detachEvent = detachEvent
            )
        }
    }

    private var observer: LifecycleEventObserver? = null

    override fun setup(lifecycle: Lifecycle, container: FragmentContainer) {
        requireMainThread()
        val observer = this.observer
        if (observer != null) {
            lifecycle.removeObserver(observer)
        }

        val newObserver = Observer(container)
        this.observer = newObserver
        lifecycle.addObserver(newObserver)
    }

    inner class Observer(private val container: FragmentContainer) : LifecycleEventObserver {
        override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
            if (observer != this) {
                source.lifecycle.removeObserver(this)
                return
            }

            if (event == attachEvent) {
                router.attachContainer(container)
            }

            if (event == detachEvent) {
                router.detachContainer()
            }
        }
    }
}