package io.sellmair.kompass.android.utils

internal inline fun <T> mainThread(crossinline action: () -> T) {
    if (isMainThread) {
        action()
    } else {
        mainThreadHandler.post { action() }
    }
}