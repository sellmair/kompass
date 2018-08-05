package io.sellmair.kompass

import android.app.Activity
import android.support.v4.app.FragmentManager

interface KompassSail {
    val activity: Activity
    val manager: FragmentManager
    val containerId: Int

    companion object
}