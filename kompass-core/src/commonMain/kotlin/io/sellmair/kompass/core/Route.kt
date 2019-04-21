package io.sellmair.kompass.core

import io.sellmair.kompass.core.RoutingStack.Element

interface Route

fun <T : Route> T.asElement(key: Key = Key()) = Element(this, key)