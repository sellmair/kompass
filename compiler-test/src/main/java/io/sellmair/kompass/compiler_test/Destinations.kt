@file:Suppress("unused")

package io.sellmair.kompass.compiler_test

import io.sellmair.kompass.annotation.Destination

/*
 Supported types:

        -  "primitives":
            - Boolean
            - Byte
            - Char
            - Short
            - Int
            - Long
            - Float
            - Double

            - BooleanArray
            - ByteArray
            - CharArray
            - ShortArray
            - IntArray
            - LongArray
            - FloatArray
            - DoubleArray

        - String
        - Array<out String>
        - Anything implementing ${ClassNames.parcelable.simpleName}
        - Anything implementing ${ClassNames.serializable.simpleName}

        - "Lists":
            - List<Boolean>
            - List<Byte>
            - List<Char>
            - List<Short>
            - List<Int>
            - List<Long>
            - List<Float>
            - List<Double>
            - List<String>
            - List<out Parcelable>

 */

@Destination
class EmptyDestination

@Destination
class BooleanDestination(val myBoolean: Boolean)

@Destination
class OptionalBooleanDestination(val myBoolean: Boolean?)

@Destination
class ByteDestination(val myByte: Byte)

@Destination
class OptionalByteDestination(val myByte: Byte?)

@Destination
class CharDestination(val myChar: Char)

@Destination
class OptionalCharDestination(val myChar: Char?)

@Destination
class ShortDestination(val myShort: Short)

@Destination
class OptionalShortDestination(val myShort: Short?)

@Destination
data class IntDestination(val myInt: Int)

@Destination
data class OptionalIntDestination(val myInt: Int)

@Destination
data class LongDestination(val myLong: Long)

@Destination
data class OptionalLongDestination(val myLong: Long?)

@Destination
data class FloatDestination(val myFloat: Float)

@Destination
data class OptionalFloatDestination(val myFloat: Float?)

@Destination
data class DoubleDestination(val myDouble: Double?)

@Destination
data class OptionalDoubleDestination(val myDouble: Double?)

@Destination
class BooleanArrayDestination(val myArray: BooleanArray)

@Destination
class OptionalBooleanArrayDestination(val myArray: BooleanArray?)

@Destination
class ByteArrayDestination(val myArray: ByteArray)

@Destination
class OptionalByteArrayDestination(val myArray: ByteArray?)

@Destination
class CharArrayDestination(val myArray: CharArray)

@Destination
class OptionalCharArrayDestination(val myArray: CharArray?)

@Destination
class ShortArrayDestination(val myArray: ShortArray)

@Destination
class OptionalShortArrayDestination(val myArray: ShortArray?)

@Destination
class IntArrayDestination(val myArray: IntArray)

@Destination
class OptionalIntArrayDestination(val myArray: IntArray?)

@Destination
class LongArrayDestination(val myArray: LongArray)

@Destination
class OptionalLongArrayDestination(val myArray: LongArray?)

@Destination
class FloatArrayDestination(val myArray: FloatArray)

@Destination
class OptionalFloatArrayDestination(val myArray: FloatArray?)

@Destination
class DoubleArrayDestination(val myArray: DoubleArray)

@Destination
class OptionalDoubleArrayDestination(val myArray: DoubleArray?)

@Destination
class StringDestination(val myString: String)

@Destination
class OptionalStringDestination(val myString: String?)

@Destination
class StringArrayDestination(val myArray: Array<String>)

@Destination
class OptionalStringArrayDestination(val myArray: Array<String>?)

@Destination
class ParcelableDestination(val myParcelable: MyParcelable)

@Destination
class OptionalParcelableDestination(val myParcelable: MyParcelable?)

@Destination
class SerializableDestination(val mySerializable: MySerializable)

@Destination
class OptionalSerializableDestination(val mySerializable: MySerializable?)

@Destination
class BooleanListDestination(val list: List<Boolean>)

@Destination
class OptionalBooleanListDestination(val list: List<Boolean>?)

@Destination
class ByteListDestination(val list: List<Byte>)

@Destination
class OptionalByteListDestination(val list: List<Byte>?)

@Destination
class CharListDestination(val list: List<Char>)

@Destination
class OptionalCharListDestination(val list: List<Char>?)

@Destination
class ShortListDestination(val list: List<Short>)

@Destination
class OptionalShortListDestination(val list: List<Short>?)

@Destination
class IntListDestination(val list: List<Int>)

@Destination
class OptionalIntListDestination(val list: List<Int>?)

@Destination
class LongListDestination(val list: List<Long>)

@Destination
class OptionalLongListDestination(val list: List<Long>?)

@Destination
class FloatListDestination(val list: List<Float>)

@Destination
class OptionalFloatListDestination(val list: List<Float>?)

@Destination
class DoubleListDestination(val list: List<Double>)

@Destination
class OptionalDoubleListDestination(val list: List<Double>?)

@Destination
class StringListDestination(val list: List<String>)

@Destination
class OptionalStringListDestination(val list: List<String>?)

@Destination
class ParcelableListDestination(val list: List<MyParcelable>)

@Destination
class OptionalParcelableListDestination(val list: List<MyParcelable>)

@Destination
class SerializableParcelableDestination(val mySerializableParcelable: MySerializableParcelable)

@Destination(target = [MyFragment::class])
class MyFragmentTargetDestination

@Destination(target = [MainActivity::class])
class MainActivityTargetDestination

@Destination(target = [MyView::class])
class MyViewTargetDestination


@Destination
class MultiArgumentDestination(
    val myInt: Int,
    val myFloat: Float,
    val mySerializable: MySerializable,
    val myParcelable: MyParcelable,
    val myStrings: List<String>?,
    val myDoubles: List<Double>?)
