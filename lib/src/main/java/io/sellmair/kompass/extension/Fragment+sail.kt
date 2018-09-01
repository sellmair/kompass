package io.sellmair.kompass.extension

import android.support.annotation.IdRes
import android.support.v4.app.Fragment
import io.sellmair.kompass.KompassSail
import io.sellmair.kompass.internal.SailImpl

fun Fragment.sail(@IdRes container: Int): KompassSail {
    return SailImpl(
        activity = this.requireActivity(),
        manager = this.childFragmentManager,
        containerId = container)
}