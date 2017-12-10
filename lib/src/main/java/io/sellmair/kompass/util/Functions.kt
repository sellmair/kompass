package io.sellmair.kompass.util

import android.os.Looper
import io.sellmair.kompass.exception.KompassException
import io.sellmair.kompass.exception.MainThreadRequiredException

/**
 * Created by sebastiansellmair on 06.12.17.
 */

internal fun requireMainThread() {
    if (Looper.myLooper() != Looper.getMainLooper()) throw KompassException(MainThreadRequiredException())
}