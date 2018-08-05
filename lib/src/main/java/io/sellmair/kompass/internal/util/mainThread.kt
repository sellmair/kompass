package io.sellmair.kompass.internal.util


internal fun mainThread(action: () -> Unit) {
    when {
        isMainThread -> action()
        else -> mainHandler.post(action)
    }
}