package io.sellmair.kompass

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction

internal class CompositeKompassDetourPilot(private val pilots: List<KompassDetourPilot>)
    : OpenDetourPilot() {
    override fun setup(destination: Any,
                       currentFragment: Fragment,
                       nextFragment: Fragment,
                       transaction: FragmentTransaction) {
        super.setup(destination, currentFragment, nextFragment, transaction)

        pilots.forEach { pilot ->
            pilot.setup(destination, currentFragment, nextFragment, transaction)
        }
    }
}