package io.sellmair.kompass.core

import io.sellmair.kompass.core.RoutingStack.Element

interface Route {
    fun asElement(key: Key = Key()) = Element(this, key)
}