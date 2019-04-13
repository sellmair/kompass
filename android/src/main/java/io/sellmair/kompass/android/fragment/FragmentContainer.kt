package io.sellmair.kompass.android.fragment

import androidx.annotation.IdRes
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager

class FragmentContainer(
    val activity: FragmentActivity,
    val fragmentManager: FragmentManager,
    @IdRes val id: Int
)