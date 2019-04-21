package io.sellmair.kompass.android.utils

import io.sellmair.kompass.android.WrongThreadException

internal fun requireMainThread() {
    if (!isMainThread) {
        throw WrongThreadException(
            "Required main thread. Found: " +
                    "${Thread.currentThread().id}: ${Thread.currentThread().name}"
        )
    }
}

