package io.sellmair.kompass.core.internal

import io.sellmair.kompass.core.Key
import io.sellmair.kompass.core.Route
import io.sellmair.kompass.core.RoutingStack

internal data class ElementImpl<T : Route>(override val route: T, override val key: Key = Key()) :
    RoutingStack.Element<T>