package io.sellmair.kompass.android.fragment

import io.sellmair.kompass.core.Route

interface PopRetainRootImmediateOrFinishSyntax : PopRetainRootImmediateSyntax,
    FragmentActivityExtension {
    fun <T : Route> FragmentRouter<T>.popRetainRootImmediateOrFinish(): Boolean {
        if (!popRetainRootImmediate()) {
            expectThisToBeAFragmentActivity().finish()
            return false
        }

        return true
    }
}