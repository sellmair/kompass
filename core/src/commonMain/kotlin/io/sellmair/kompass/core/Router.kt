package io.sellmair.kompass.core

typealias RoutingStackManipulation<T> = RoutingStack<T>.() -> RoutingStack<T>

interface Router<T : Route> {
    fun execute(instruction: RoutingStackManipulation<T>)
}

