package io.sellmair.kompass.internal

import android.app.Activity
import android.support.v4.app.FragmentManager
import io.sellmair.kompass.KompassSail


internal data class SailImpl(
    override val activity: Activity,
    override val manager: FragmentManager,
    override val containerId: Int) : KompassSail