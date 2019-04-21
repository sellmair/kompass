package io.sellmair.kompass.core

import io.sellmair.kompass.core.RoutingStack.Element


@DslMarker
annotation class RoutingStackInstructionMarker

@RoutingStackInstructionMarker
interface PlainStackInstructionSyntax<T : Route, R> {
    fun plainStackInstruction(instruction: PlainStackInstruction<T>): R
}


@RoutingStackInstructionMarker
infix fun <T : Route, R> PlainStackInstructionSyntax<T, R>.push(route: T): R = plainStackInstruction {
    this + Element(route)
}

@RoutingStackInstructionMarker
infix fun <T : Route, R> PlainStackInstructionSyntax<T, R>.pushDistinct(route: T): R = plainStackInstruction {
    val top = lastOrNull { it.route == route } ?: Element(route)
    filterNot { it.route == route } + top
}

@RoutingStackInstructionMarker
infix fun <T : Route, R> PlainStackInstructionSyntax<T, R>.push(element: Element<T>): R = plainStackInstruction {
    filterNot { it.key == element.key } + element
}

@RoutingStackInstructionMarker
fun <T : Route, R> PlainStackInstructionSyntax<T, R>.clear(): R = plainStackInstruction {
    emptyList()
}

@RoutingStackInstructionMarker
fun <T : Route, R> PlainStackInstructionSyntax<T, R>.pop(): R = plainStackInstruction {
    if (isEmpty()) emptyList()
    else subList(0, lastIndex)
}

@RoutingStackInstructionMarker
inline infix fun <T : Route, R> PlainStackInstructionSyntax<T, R>.popUntil(
    crossinline predicate: (T) -> Boolean
): R = plainStackInstruction {
    if (isEmpty()) emptyList()
    else dropLastWhile { element -> !predicate(element.route) }
}

@RoutingStackInstructionMarker
infix fun <T : Route, R> PlainStackInstructionSyntax<T, R>.popUntilRoute(route: T): R {
    return popUntil { it == route }
}
