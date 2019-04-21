package io.sellmair.kompass.android.fragment.setup

import androidx.fragment.app.Fragment
import io.sellmair.kompass.android.fragment.KompassFragment
import io.sellmair.kompass.core.exception.KompassException

interface FragmentExtensions

/* KEEP-87 */
internal fun FragmentExtensions.expectThisToBeAFragment() = this as? Fragment ?: throw KompassException(
    "${KompassFragment::class.java.simpleName} only works for Fragments"
)