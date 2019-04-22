package io.sellmair.kompass.core

/**
 * # PlainStackInstruction
 * Function that describes a manipulation of a [RoutingStack] by receiving a list of elements
 * to create a new list.
 */
typealias PlainStackInstruction<T> = List<RoutingStack.Element<T>>.() -> Iterable<RoutingStack.Element<T>>
