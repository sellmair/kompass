package io.sellmair.kompass.internal.pipe

import android.support.annotation.AnyThread
import android.support.annotation.UiThread
import io.sellmair.kompass.internal.precondition.Precondition
import io.sellmair.kompass.internal.precondition.requireMainThread
import io.sellmair.kompass.internal.util.mainThread


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

    private var handler: ((T) -> Unit)? = null

    @UiThread
    override fun handle(handler: (T) -> Unit) {
        Precondition.requireMainThread()
        this.handler = handler
    }

    @AnyThread
    override fun handle(value: T) = mainThread {
        handler?.invoke(value)
    }

}



