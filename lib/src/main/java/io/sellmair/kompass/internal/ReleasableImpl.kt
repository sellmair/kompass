package io.sellmair.kompass.internal

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.OnLifecycleEvent
import io.sellmair.kompass.KompassReleasable
import io.sellmair.kompass.internal.util.mainThread

private class ReleasableImpl(private val action: () -> Unit) : KompassReleasable {
    var isReleased = false

    override fun release() = mainThread {
        if (isReleased) return@mainThread
        action()
        isReleased = true
    }

    override fun releasedBy(lifecycle: Lifecycle) {
        lifecycle.addObserver(Observer())
    }

    override fun releasedBy(lifecycleOwner: LifecycleOwner) {
        releasedBy(lifecycleOwner.lifecycle)
    }

    private inner class Observer : LifecycleObserver {
        @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
        fun onPause() {
            release()
        }
    }
}

fun releasable(action: () -> Unit): KompassReleasable {
    return ReleasableImpl(action)
}
