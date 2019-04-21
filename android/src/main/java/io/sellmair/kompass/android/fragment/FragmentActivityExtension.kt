package io.sellmair.kompass.android.fragment

import androidx.fragment.app.FragmentActivity
import io.sellmair.kompass.core.exception.KompassException


/* Keep 87 */
interface FragmentActivityExtension

internal fun FragmentActivityExtension.expectThisToBeAFragmentActivity() =
    this as? FragmentActivity ?: throw KompassException(
        "${KompassFragmentActivity::class.java.simpleName} only works for Fragments"
    )