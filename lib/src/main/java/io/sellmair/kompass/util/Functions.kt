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


inline fun <R> tryOrNull(block: () -> R): R? {
    return try {
        block()
    } catch (e: Throwable) {
        null
    }
}

inline fun <R> R.applyIf(condition: Boolean, body: R.() -> Unit): R {
    if (condition) {
        this.body()
    }

    return this
}