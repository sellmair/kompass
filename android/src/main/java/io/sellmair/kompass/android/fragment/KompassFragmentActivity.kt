package io.sellmair.kompass.android.fragment

import androidx.annotation.IdRes
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import io.sellmair.kompass.core.KompassException

interface KompassFragmentActivity {

    fun FragmentRouter<*>.setup(
        @IdRes containerId: Int,
        fragmentManager: FragmentManager = expectThisToBeAFragmentActivity().supportFragmentManager
    ) = setup(
        activity = expectThisToBeAFragmentActivity(),
        containerId = containerId,
        fragmentManager = fragmentManager
    )

    /* Keep 87 */
    private fun expectThisToBeAFragmentActivity() = this as? FragmentActivity ?: throw KompassException(
        "${KompassFragmentActivity::class.java.simpleName} only works for Fragments"
    )
}