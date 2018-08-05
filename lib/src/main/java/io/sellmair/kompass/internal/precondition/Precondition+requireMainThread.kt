@file:Suppress("unused")

package io.sellmair.kompass.internal.precondition

import io.sellmair.kompass.internal.util.isMainThread

internal fun Precondition.requireMainThread() {
    if (!isMainThread) {
        throwMainThreadRequiredException()
    }
}

private fun throwMainThreadRequiredException() {
    throw KompassPreconditionException("Function not called from main thread")
}