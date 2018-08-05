package io.sellmair.kompass

import android.support.annotation.AnyThread
import android.support.annotation.UiThread

interface KeyLessBackStack {
    @AnyThread
    fun back()

    @UiThread
    fun backImmediate(): Boolean

    @AnyThread
    fun onBack(action: () -> Unit)
}