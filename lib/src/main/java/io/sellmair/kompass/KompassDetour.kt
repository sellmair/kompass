package io.sellmair.kompass

import android.support.v4.app.FragmentTransaction


sealed class KompassDetour<in Destination>

abstract class KompassFragmentDetour<in Destination, in CurrentFragment, in NextFragment> :
    KompassDetour<Destination>() {

    abstract fun setup(
        destination: Destination,
        currentFragment: CurrentFragment,
        nextFragment: NextFragment,
        transaction: FragmentTransaction)

}

abstract class KompassViewDetour<in Destination, in CurrentView, in NextView> :
    KompassDetour<Destination>() {
    abstract fun setup(
        destination: Destination,
        currentView: CurrentView,
        nextView: NextView): KompassViewAnimation
}