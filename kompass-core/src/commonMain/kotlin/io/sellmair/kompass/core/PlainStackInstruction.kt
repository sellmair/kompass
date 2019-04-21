package io.sellmair.kompass.core

typealias PlainStackInstruction<T> = List<RoutingStack.Element<T>>.() -> Iterable<RoutingStack.Element<T>>
