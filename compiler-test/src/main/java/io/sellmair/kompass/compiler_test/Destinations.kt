package io.sellmair.kompass.compiler_test

import io.sellmair.kompass.annotation.Destination

@Destination
class EmptyDestination

@Destination
data class IntDestination(val myInt: Int)

@Destination
data class StringDestination(val myString: String)

@Destination
data class FloatDestination(val myFloat: Float)

@Destination
data class StringListDestination(val strings: List<String>)

@Destination
data class NullableIntDestination(val myNullableInt: Int?)