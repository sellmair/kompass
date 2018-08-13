package io.sellmair.kompass.compiler

import com.squareup.kotlinpoet.asTypeName
import javax.lang.model.type.TypeMirror

internal class KompassUnsupportedDestinationTypeException(type: TypeMirror) : Exception("""

    Type ${type.asTypeName()}
    is not supported for destinations.

    Supported types:

        -  "primitives":
            Boolean, Byte, Char, Short, Int, Long, Float, Double
            BooleanArray, ByteArray, CharArray, ShortArray, IntArray, LongArray, FloatArray, DoubleArray

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



""".trimIndent())