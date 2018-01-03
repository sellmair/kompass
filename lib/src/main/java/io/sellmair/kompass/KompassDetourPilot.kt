package io.sellmair.kompass

import android.support.v4.app.Fragment
import kotlin.reflect.KClass

/**
 * Created by sebastiansellmair on 03.01.18.
 */
interface KompassDetourPilot : KompassDetour<Any, Fragment, Fragment> {
    fun <Destination : Any, CurrentFragment : Fragment, NextFragment : Fragment>
            registerDetour(detour: KompassDetour<Destination, CurrentFragment, NextFragment>,
                           destinationClass: KClass<Destination>,
                           currentFragmentClass: KClass<CurrentFragment>,
                           nextFragmentClass: KClass<NextFragment>)

    companion object {
        fun create(): KompassDetourPilot = OpenDetourPilot()
    }
}

inline fun <reified Destination : Any, reified CurrentFragment : Fragment, reified NextFragment : Fragment>
        KompassDetourPilot.registerDetour(detour: KompassDetour<Destination, CurrentFragment, NextFragment>) {
    this.registerDetour(detour, Destination::class, CurrentFragment::class, NextFragment::class)
}