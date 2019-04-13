package io.sellmair.kompass.core

import kotlin.random.Random

inline class Key(val value: String = randomRouteKeyValue())

private fun randomRouteKeyValue() = Random.nextBytes(16)
    .map { byte -> byte.toInt() and 0xFF }
    .joinToString("") { it.toString(16) }
