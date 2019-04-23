package io.sellmair.kompass.core

import io.sellmair.kompass.core.RoutingStack.Element

/**
 * # Route
 * Interface that marks classes to be suitable for routing.
 */
interface Route


/**
 * @return A default implementation of [RoutingStack.Element] for this given route with the specified [key].
 * If no [key] was specified, then a new random key will be created.
 */
fun <T : Route> T.asElement(key: Key = Key()) = Element(this, key)