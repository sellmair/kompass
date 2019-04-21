package io.sellmair.kompass.core

import io.sellmair.kompass.core.RoutingStack.Element

typealias RoutingStackInstruction<T> = List<Element<T>>.() -> List<Element<T>>

@DslMarker
annotation class RoutingStackInstructionMarker

@RoutingStackInstructionMarker
interface RoutingStackInstructionSyntax<T : Route, R> {
    fun stackInstruction(instruction: RoutingStackInstruction<T>): R
}


@RoutingStackInstructionMarker
infix fun <T : Route, R> RoutingStackInstructionSyntax<T, R>.push(route: T): R = stackInstruction {
    this + Element(route)
}

@RoutingStackInstructionMarker
infix fun <T : Route, R> RoutingStackInstructionSyntax<T, R>.pushDistinct(route: T): R = stackInstruction {
    val top = lastOrNull { it.route == route } ?: Element(route)
    filterNot { it.route == route } + top
}

@RoutingStackInstructionMarker
infix fun <T : Route, R> RoutingStackInstructionSyntax<T, R>.push(element: Element<T>): R = stackInstruction {
    filterNot { it.key == element.key } + element
}

@RoutingStackInstructionMarker
fun <T : Route, R> RoutingStackInstructionSyntax<T, R>.clear(): R = stackInstruction {
    emptyList()
}

@RoutingStackInstructionMarker
fun <T : Route, R> RoutingStackInstructionSyntax<T, R>.pop(): R = stackInstruction {
    if (isEmpty()) emptyList()
    else subList(0, lastIndex)
}

@RoutingStackInstructionMarker
inline infix fun <T : Route, R> RoutingStackInstructionSyntax<T, R>.popUntil(
    crossinline predicate: (T) -> Boolean
): R = stackInstruction {
    if (isEmpty()) emptyList()
    else dropLastWhile { element -> !predicate(element.route) }
}

@RoutingStackInstructionMarker
infix fun <T : Route, R> RoutingStackInstructionSyntax<T, R>.popUntilRoute(route: T): R {
    return popUntil { it == route }
}
