package io.sellmair.kompass.internal.pipe

import android.support.annotation.AnyThread
import io.sellmair.kompass.internal.util.mainThread
import java.util.concurrent.atomic.AtomicReference


/*
################################################################################################
INTERNAL API
################################################################################################
*/

internal fun <T> Handleable.Companion.delegate(): Handleable<T> = HandleableImpl()


/*
################################################################################################
PRIVATE IMPLEMENTATION
################################################################################################
*/

private class HandleableImpl<T> : Handleable<T> {

    private val handler: AtomicReference<((T) -> Unit)> = AtomicReference()

    @AnyThread
    override fun handle(handler: (T) -> Unit) {
        this.handler.set(handler)
    }

    @AnyThread
    override fun handle(value: T) = mainThread {
        handler.get()?.invoke(value)
    }

}



