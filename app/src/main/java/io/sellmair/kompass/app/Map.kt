package io.sellmair.kompass.app

import io.sellmair.kompass.KompassMap
import io.sellmair.kompass.KompassRoute
import io.sellmair.kompass.asRoute

class Map : KompassMap<Destination> {
    override fun get(destination: Destination): KompassRoute? {
        return when (destination.id) {
            Target.FragmentOne -> FragmentOne().asRoute()
            Target.FragmentTwo -> FragmentTwo().asRoute()
            Target.FragmentThree -> FragmentThree().asRoute()
            Target.FragmentFour -> FragmentFour().asRoute()
            Target.FragmentFive -> FragmentFive().asRoute()
            Target.FragmentSix -> FragmentSix().asRoute()
            Target.FragmentSeven -> FragmentSeven().asRoute()
            Target.FragmentEight -> FragmentEight().asRoute()
            Target.FragmentNine -> FragmentNine().asRoute()
            Target.FragmentTen -> FragmentTen().asRoute()
        }
    }
}