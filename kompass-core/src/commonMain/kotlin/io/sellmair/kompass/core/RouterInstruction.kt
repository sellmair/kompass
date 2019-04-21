package io.sellmair.kompass.core

/**
 * # RouterInstruction
 * A [Router] can execute arbitrary changes in the [RoutingStack].
 * All instructions that one can send to a [Router] are represented as a function from the "current"
 * [RoutingStack] to the "desired" one.
 *
 * ## Note
 * - This function has to be pure
 * - This function should not have any side-effects
 * - This function should not mutate the "current" [RoutingStack], but create a new one.
 */
typealias RouterInstruction<T> = RoutingStack<T>.() -> RoutingStack<T>