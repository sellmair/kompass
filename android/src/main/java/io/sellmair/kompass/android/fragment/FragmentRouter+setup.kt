package io.sellmair.kompass.android.fragment

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle

fun FragmentRouter<*>.setup(
    activity: FragmentActivity,
    @IdRes containerId: Int,
    fragmentManager: FragmentManager = activity.supportFragmentManager
) = setup(
    activity.lifecycle,
    FragmentContainer(activity, fragmentManager, containerId)
)


fun FragmentRouter<*>.setup(
    fragment: Fragment,
    @IdRes containerId: Int,
    fragmentManager: FragmentManager = fragment.childFragmentManager
) = setup(
    fragment.lifecycle,
    FragmentContainer(
        fragment.requireActivity(),
        fragmentManager,
        containerId
    )
)


internal fun FragmentRouter<*>.setup(lifecycle: Lifecycle, container: FragmentContainer) {
    fragmentContainerLifecycle.setup(lifecycle, container)
}


