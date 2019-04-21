package io.sellmair.kompass.core

typealias RoutingStackInstruction<T> = RoutingStack<T>.() -> RoutingStack<T>

operator fun <T : Route> RoutingStackInstruction<T>.plus(
    other: RoutingStackInstruction<T>
): RoutingStackInstruction<T> {
    return {
        this@plus.invoke(this)
        other.invoke(this)
    }
}

@Suppress("FunctionName")
fun <T: Route> EmptyRoutingStackInstruction(): RoutingStackInstruction<T> = { this }