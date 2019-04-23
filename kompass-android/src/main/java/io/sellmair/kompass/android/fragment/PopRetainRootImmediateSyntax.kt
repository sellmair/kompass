package io.sellmair.kompass.android.fragment

import io.sellmair.kompass.core.Route
import io.sellmair.kompass.core.exception.KompassException
import io.sellmair.kompass.core.pop

interface PopRetainRootImmediateSyntax {

    class NotAttachedException(message: String?) : KompassException(message)


    /**
     * Will pop a single element from the routing stack, but will never pop the `root` (the last remaining route)
     * @return true: If the event was consumed an a route was popped
     * false: If routing stack has equal or less than one element left and no route was popped
     */
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
        "popRetainRootImmediate can only be called in the correct lifecycle state and after router.setup"
    )
}
