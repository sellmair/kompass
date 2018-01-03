package io.sellmair.kompass.app

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import io.sellmair.kompass.KompassDetour
import io.sellmair.kompass.annotation.Destination
import io.sellmair.kompass.annotation.Detour
import io.sellmair.kompass.app.model.SimpleParcelable

/**
 * Created by sebastiansellmair on 10.12.17.
 */
@Destination(target = [SimpleDestinationFragment::class])
data class SimpleDestination(val id: Int, val justALong: Long, val name: String)

@Destination
class SimpleParcelDestination(val id: Int, val parcel: SimpleParcelable)

@Destination(target = [Fragment::class])
class SimpleIntListDestination(val name: String, val ids: List<Int>)

class SimpleIntArrayDestination(val name: String, val ids: IntArray)

@Destination(target = [MainActivity::class])
class MaltesMostWantedDestination(val name: String, val ids: List<Int>, val myParcel: SimpleParcelable)


@Destination(target = [JuliansDestinationFragment::class])
class JuliansDestination(val name: String, val ids: List<Int>)


class SimpleDestinationFragment : Fragment()
class JuliansDestinationFragment : Fragment()

@Detour
class TestDetour() : KompassDetour<Any, JuliansDestinationFragment, SimpleDestinationFragment> {
    override fun setup(destination: Any,
                       currentFragment: JuliansDestinationFragment,
                       nextFragment: SimpleDestinationFragment,
                       transaction: FragmentTransaction) {

    }

}