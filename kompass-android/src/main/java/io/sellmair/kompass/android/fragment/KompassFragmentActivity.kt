package io.sellmair.kompass.android.fragment

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.fragment.app.FragmentManager
import io.sellmair.kompass.android.fragment.setup.ActivityFragmentRouterSetupSyntax

interface KompassFragmentActivity :
    FragmentActivityExtension,
    PopRetainRootImmediateSyntax,
    PopRetainRootImmediateOrFinishSyntax {

    fun FragmentRouter<*>.setup(
        savedInstanceState: Bundle?, @IdRes containerId: Int,
        fragmentManager: FragmentManager = expectThisToBeAFragmentActivity().supportFragmentManager
    ) {
        ActivityFragmentRouterSetupSyntax(expectThisToBeAFragmentActivity()).run {
            setup(savedInstanceState, containerId, fragmentManager)
        }
    }

}


