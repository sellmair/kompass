package io.sellmair.kompass.core.internal

import io.sellmair.kompass.core.Route
import io.sellmair.kompass.core.RoutingStack
import io.sellmair.kompass.core.RoutingStack.Element

internal data class RoutingStackImpl<T : Route>(override val elements: List<Element<T>>) : RoutingStack<T> {

    override fun with(elements: Iterable<Element<T>>): RoutingStack<T> {
        return if (elements == this.elements) this
        else copy(elements = elements.toList())
    }

    init {
        check(elements.groupBy(Element<*>::key).none { (_, routes) -> routes.size > 1 }) {
            "RoutingStack cannot contain entries with the same key twice"
        }
    }

}