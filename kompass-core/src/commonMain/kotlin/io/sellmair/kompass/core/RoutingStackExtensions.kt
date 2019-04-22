package io.sellmair.kompass.core

import io.sellmair.kompass.core.RoutingStack.Element

/**
 * Convenience function to work on the [Route] objects directly
 */
val <T : Route> RoutingStack<T>.routes get() = elements.map(RoutingStack.Element<T>::route)

/**
 * @return Whether or not the [RoutingStack] contains the specified [key]
 */
operator fun RoutingStack<*>.contains(key: Key): Boolean {
    return this.elements.any { it.key == key }
}

/**
 * @return Whether or not the [RoutingStack] contains the specified [element].
 *
 * ## Note
 * The element will be compared by route and key (not just key)
 * @see Element
 */
operator fun RoutingStack<*>.contains(element: RoutingStack.Element<*>): Boolean {
    return this.elements.any { it == element }
}

/**
 * @return Whether or not the [RoutingStack] contains the specified [route]
 *
 * ## Note
 * Routes may not be distinct in the [RoutingStack] It is possible, that the a stack
 * contains a given route multiple times
 */
operator fun RoutingStack<*>.contains(route: Route): Boolean {
    return this.routes.contains(route)
}