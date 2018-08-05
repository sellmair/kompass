package io.sellmair.kompass

import android.support.annotation.AnyThread
import android.support.annotation.UiThread

interface BackStack {

    @AnyThread
    fun back(key: Any? = null)

    @UiThread
    fun backImmediate(key: Any? = null): Boolean

    @AnyThread
    fun onBack(key: Any? = null, keySingle: Boolean = false, action: () -> Unit)

    @AnyThread
    fun remove(key: Any)

    @AnyThread
    fun clear()

}
