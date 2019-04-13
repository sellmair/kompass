package io.sellmair.kompass.core.internal

import io.sellmair.kompass.core.Route
import io.sellmair.kompass.core.RoutingStack
import io.sellmair.kompass.core.RoutingStack.Element
import io.sellmair.kompass.core.push

internal data class RoutingStackImpl<T : Route>(override val elements: List<Element<T>>) : RoutingStack<T> {

    /* KEEP-87 Type classes for the rescue */

    operator fun T.unaryPlus() = push(this)

    operator fun plus(route: T) = push(route)

    override fun with(elements: Iterable<Element<T>>): RoutingStack<T> {
        return copy(elements = elements.toList())
    }

    init {
        check(elements.groupBy(Element<*>::key).none { (_, routes) -> routes.size > 1 }) {
            "RoutingStack cannot contain entries with the same key twice"
        }
    }

}