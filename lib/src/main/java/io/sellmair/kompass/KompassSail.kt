package io.sellmair.kompass

import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentManager

interface KompassSail {
    val activity: FragmentActivity
    val fragmentManager: FragmentManager
    val containerId: Int
    fun release()
}