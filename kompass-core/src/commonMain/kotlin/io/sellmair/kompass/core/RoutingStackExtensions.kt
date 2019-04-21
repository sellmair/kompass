package io.sellmair.kompass.core

val <T : Route> RoutingStack<T>.routes get() = elements.map(RoutingStack.Element<T>::route)

operator fun RoutingStack<*>.contains(key: Key): Boolean {
    return this.elements.any { it.key == key }
}

operator fun RoutingStack<*>.contains(element: RoutingStack.Element<*>): Boolean {
    return this.elements.any { it == element }
}

operator fun RoutingStack<*>.contains(route: Route): Boolean {
    return this.routes.contains(route)
}