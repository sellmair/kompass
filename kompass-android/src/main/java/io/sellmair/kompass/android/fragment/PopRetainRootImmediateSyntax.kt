package io.sellmair.kompass.android.fragment

import io.sellmair.kompass.core.Route
import io.sellmair.kompass.core.exception.KompassException
import io.sellmair.kompass.core.pop

interface PopRetainRootImmediateSyntax {

    class NotAttachedException(message: String?) : KompassException(message)

    fun <T : Route> FragmentRouter<T>.popRetainRootImmediate(): Boolean {
        var didPop: Boolean? = null

        instruction {
            if (count() <= 1) {
                didPop = false
                this
            } else {
                didPop = true
                pop()
            }
        }

        return didPop ?: throwNotAttachedException()
    }
}


private fun throwNotAttachedException(): Nothing {
    throw PopRetainRootImmediateSyntax.NotAttachedException(
        "popRetainRootImmediate can only be called between `onResume` and `onPause` and after router.setup"
    )
}
