package io.sellmair.kompass.extension

import android.support.annotation.IdRes
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentManager
import io.sellmair.kompass.KompassSail
import io.sellmair.kompass.internal.SailImpl

@Suppress("unused")
fun FragmentActivity.sail(@IdRes container: Int,
                          manager: FragmentManager? = null): KompassSail {
    return SailImpl(
        activity = this,
        manager = manager ?: supportFragmentManager,
        containerId = container)
}