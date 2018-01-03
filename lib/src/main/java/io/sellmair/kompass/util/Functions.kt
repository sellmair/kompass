package io.sellmair.kompass.util

import android.os.Handler
import android.os.Looper
import io.sellmair.kompass.exception.KompassException
import io.sellmair.kompass.exception.MainThreadRequiredException

/**
 * Created by sebastiansellmair on 06.12.17.
 */

internal val handler by lazy { Handler(Looper.getMainLooper()) }

internal fun requireMainThread() {
    if (!isMainThread()) throw KompassException(MainThreadRequiredException())
}

internal fun isMainThread(): Boolean = Looper.myLooper() == Looper.getMainLooper()

internal fun mainThread(block: () -> Unit) {
    if (isMainThread()) block()
    else handler.post { block() }
}

val Any?.unit get() = Unit


inline fun <R> tryOrNull(block: () -> R): R? {
    try {
        return block()
    } catch (e: Throwable) {
        return null
    }
}