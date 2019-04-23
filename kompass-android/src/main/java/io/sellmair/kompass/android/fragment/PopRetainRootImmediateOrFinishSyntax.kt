package io.sellmair.kompass.android.fragment

import io.sellmair.kompass.core.Route

interface PopRetainRootImmediateOrFinishSyntax : PopRetainRootImmediateSyntax, FragmentActivityExtension {

    /**
     * Same as [popRetainRootImmediate], but will also finish the current activity if no routes are left to pop
     * @return true: If a route was popped
     * false: If no route (except the root) was left to pop and the activity was finished
     * @see popRetainRootImmediate
     */
    fun <T : Route> FragmentRouter<T>.popRetainRootImmediateOrFinish(): Boolean {
        if (!popRetainRootImmediate()) {
            expectThisToBeAFragmentActivity().finish()
            return false
        }

        return true
    }
}