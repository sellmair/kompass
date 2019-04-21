package io.sellmair.kompass.android.fragment

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import io.sellmair.kompass.android.fragment.setup.ActivityFragmentRouterSetupSyntax
import io.sellmair.kompass.core.exception.KompassException

interface KompassFragmentActivity {

    fun FragmentRouter<*>.setup(
        savedInstanceState: Bundle?, @IdRes containerId: Int,
        fragmentManager: FragmentManager = expectThisToBeAFragmentActivity().supportFragmentManager
    ) {
        ActivityFragmentRouterSetupSyntax(expectThisToBeAFragmentActivity()).run {
            setup(savedInstanceState, containerId, fragmentManager)
        }
    }


    /* Keep 87 */
    private fun expectThisToBeAFragmentActivity() = this as? FragmentActivity ?: throw KompassException(
        "${KompassFragmentActivity::class.java.simpleName} only works for Fragments"
    )

}


