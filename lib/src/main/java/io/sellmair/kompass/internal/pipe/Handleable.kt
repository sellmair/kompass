package io.sellmair.kompass.internal.pipe

import android.support.annotation.AnyThread


internal interface Handleable<T> {

    @AnyThread
    fun handle(handler: (T) -> Unit)

    @AnyThread
    fun handle(value: T)

    companion object
}

