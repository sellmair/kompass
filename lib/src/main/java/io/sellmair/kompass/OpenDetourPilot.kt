package io.sellmair.kompass

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import io.sellmair.kompass.util.requireMainThread
import kotlin.reflect.KClass

/**
 * Created by sebastiansellmair on 03.01.18.
 */
open class OpenDetourPilot : KompassDetourPilot {

    private val reifiedDetours = mutableListOf<ReifiedDetour<*, *, *>>()

    override fun <Destination : Any,
            CurrentFragment : Fragment,
            NextFragment : Fragment> registerDetour(detour: KompassDetour<Destination,
            CurrentFragment, NextFragment>, destinationClass: KClass<Destination>,
                                                    currentFragmentClass: KClass<CurrentFragment>,
                                                    nextFragmentClass: KClass<NextFragment>) {
        requireMainThread()
        reifiedDetours.add(ReifiedDetour(detour, destinationClass, currentFragmentClass, nextFragmentClass))
    }

    override fun setup(destination: Any,
                       currentFragment: Fragment,
                       nextFragment: Fragment,
                       transaction: FragmentTransaction) {

        reifiedDetours.asSequence()
                .first { it.trySetup(destination, currentFragment, nextFragment, transaction) }
    }


    private data class ReifiedDetour<Destination : Any, CurrentFragment : Any, NextFragment : Any>
    (private val detour: KompassDetour<Destination, CurrentFragment, NextFragment>,
     private val destinationClass: KClass<Destination>,
     private val currentFragmentClass: KClass<CurrentFragment>,
     private val nextFragmentClass: KClass<NextFragment>) {
        fun trySetup(destination: Any,
                     currentFragment: Fragment,
                     nextFragment: Fragment,
                     transaction: FragmentTransaction): Boolean {
            if (destinationClass.java.isInstance(destination)
                    && currentFragmentClass.java.isInstance(currentFragment)
                    && nextFragmentClass.java.isInstance(nextFragment)) {

                @Suppress("UNCHECKED_CAST")
                detour.setup(destination as Destination,
                        currentFragment as CurrentFragment,
                        nextFragment as NextFragment,
                        transaction)

                return true
            }

            return false
        }
    }
}