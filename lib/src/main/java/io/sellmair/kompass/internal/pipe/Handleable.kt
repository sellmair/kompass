package io.sellmair.kompass.internal.pipe

import android.support.annotation.AnyThread
import android.support.annotation.UiThread


internal interface Handleable<T> {

    @UiThread
    fun handle(handler: (T) -> Unit)

    @AnyThread
    fun handle(value: T)

    companion object
}

