package io.sellmair.kompass.app

import android.os.Bundle
import android.support.v4.app.Fragment
import io.sellmair.kompass.annotation.Destination
import io.sellmair.kompass.annotation.KompassConstructor
import io.sellmair.kompass.app.model.SimpleParcelable
import io.sellmair.kompass.asMyNewDestination

/**
 * Created by sebastiansellmair on 10.12.17.
 */
@Destination(target = [SimpleDestinationFragment::class])
data class SimpleDestination(val id: Int, val justALong: Long, val name: String)

@Destination
class SimpleParcelDestination(val id: Int, val parcel: SimpleParcelable)

@Destination
class ParcelableArrayDestination(val name: String, val parcels: Array<SimpleParcelable>)

@Destination
class ParcelableListDestination(val name: String, val parcels: List<SimpleParcelable>)


@Destination(target = [Fragment::class])
class SimpleIntListDestination(val name: String, val ids: List<Int>)

@Destination
class SimpleIntArrayDestination(val name: String, val ids: IntArray)

@Destination
class StringListDestination(val id: Long, val names: List<String>)


@Destination
class StringArrayDestination(val id: Long, val names: Array<String>)

@Destination
class PrimitiveArrayDestination(
        val intArray: IntArray,
        val floatArray: FloatArray,
        val shortArray: ShortArray,
        val doubleArray: DoubleArray,
        val byteArray: ByteArray,
        val charArray: CharArray,
        val booleanArray: BooleanArray,
        val longArray: LongArray
)

@Destination
class SimpleFloatListDestination(val name: String, val ids: List<Float>)

@Destination
class SimpleDoubleListDestination(val name: String, val ids: List<Double>)

@Destination
class SimpleCharListDestination(val name: String, val ids: List<Char>)

@Destination
class SimpleBooleanListDestination(val name: String, val ids: List<Boolean>)

@Destination
class SimpleLongListDestination(val name: String, val ids: List<Long>)

@Destination
class SimpleShortListDestination(val name: String, val ids: List<Short>)

@Destination(target = [MainActivity::class])
class MaltesMostWantedDestination(val name: String, val ids: List<Int>,
                                  val myParcel: SimpleParcelable)


@Destination(target = [JuliansDestinationFragment::class])
data class JuliansDestination(val name: String, val ids: List<Int>)


@Destination(target = [JuliansDestinationFragment::class])
data class MyNewDestination(val name: String, val ids: List<Int>)


class SimpleDestinationFragment : Fragment()
class JuliansDestinationFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments.asMyNewDestination().name
    }
}

@Destination
class OptionalIntDestination(val id: Int?, val name: String)

@Destination
data class OptionalPrimitiveDestination @KompassConstructor constructor(
        val id: Int?,
        val someFloat: Float?,
        val someDouble: Double?,
        val someShort: Short?,
        val someBoolean: Boolean?
)

@Destination
class DefaultValueDestination(
    val id: Int? = null,
    val myFloat: Float? = null
)