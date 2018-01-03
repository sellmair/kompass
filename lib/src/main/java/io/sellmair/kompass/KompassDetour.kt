package io.sellmair.kompass

import android.support.v4.app.FragmentTransaction

/**
 * Created by sebastiansellmair on 03.01.18.
 */
interface KompassDetour<in Destination, in CurrentFragment, in NextFragment> {
    fun setup(destination: Destination,
              currentFragment: CurrentFragment,
              nextFragment: NextFragment,
              transaction: FragmentTransaction)
}