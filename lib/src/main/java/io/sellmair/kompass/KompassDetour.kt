package io.sellmair.kompass

import android.support.v4.app.FragmentTransaction

@Deprecated("Use KompassFragmentDetour instead")
typealias KompassDetour<Destination, CurrentFragment, NextFragment> =
    KompassFragmentDetour<Destination, CurrentFragment, NextFragment>

interface KompassFragmentDetour<in Destination, in CurrentFragment, in NextFragment> {
    abstract fun setup(
        destination: Destination,
        currentFragment: CurrentFragment,
        nextFragment: NextFragment,
        transaction: FragmentTransaction)

}

interface KompassViewDetour<in Destination, in CurrentView, in NextView> {
    abstract fun setup(
        destination: Destination,
        currentView: CurrentView,
        nextView: NextView): KompassViewAnimation
}