package io.sellmair.kompass.core

import kotlin.random.Random

/**
 * # Key
 * Key objects are used to identify other objects (routes in particular)
 * The [value] of the key will be used for comparison of [Key] objects.
 *
 * ## Note
 * - [Key] sub-implementations cannot override the behaviour of the [equals] or [hashCode] function
 * - [Key] implementations are required to be immutable and not changing over time.
 */
open class Key {

    open val value: String = randomKeyValue()

    final override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Key) return false
        if (value != other.value) return false
        return true
    }

    final override fun hashCode(): Int {
        return value.hashCode()
    }

    companion object Factory

}

/**
 * Creates a new 128-bit long random string, which can be used as value for [Key] objects.
 */
fun Key.Factory.randomKeyValue() = Random.nextBytes(16)
    .map { byte -> byte.toInt() and 0xFF }
    .joinToString("") { it.toString(16) }


/**
 * @return A default implementation of [Key] that allows for a pre-defined [Key] value.
 */
operator fun Key.Factory.invoke(value: String): Key = DefinedKey(value)


/**
 * Default implementation of [Key] which allows a pre-defined [Key] value.
 */
private class DefinedKey(override val value: String) : Key()