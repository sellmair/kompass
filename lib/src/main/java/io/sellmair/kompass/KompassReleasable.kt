package io.sellmair.kompass

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleOwner
import android.support.annotation.AnyThread
import android.support.annotation.UiThread

interface KompassReleasable {
    @AnyThread
    fun release()

    @UiThread
    fun releasedBy(lifecycle: Lifecycle)

    @UiThread
    fun releasedBy(lifecycleOwner: LifecycleOwner)
}

