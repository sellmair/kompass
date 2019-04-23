package io.sellmair.kompass.core

import io.sellmair.kompass.core.RoutingStack.Element


@DslMarker
annotation class RoutingStackInstructionMarker


@RoutingStackInstructionMarker
interface PlainStackInstructionSyntax<T : Route, R> {
    fun plainStackInstruction(instruction: PlainStackInstruction<T>): R
}

/**
 * Will push the [route] to the top of the stack.
 *
 * ## Note
 * - This operation is not distinct: Meaning, that if the route is already present in the stack,
 * it will simply be duplicated
 *
 * @see pushDistinct
 */
@RoutingStackInstructionMarker
infix fun <T : Route, R> PlainStackInstructionSyntax<T, R>.push(route: T): R = plainStackInstruction {
    this + Element(route)
}


/**
 * Will push the [route] to the top of the stack, but will make sure that the route is only present once in the stack.
 *
 * ## Note
 * - All occurrences of the [route] in the current stack will be removed so that the route is just present at the top
 */
@RoutingStackInstructionMarker
infix fun <T : Route, R> PlainStackInstructionSyntax<T, R>.pushDistinct(route: T): R = plainStackInstruction {
    val top = lastOrNull { it.route == route } ?: Element(route)
    filterNot { it.route == route } + top
}

/**
 * Will push the [element] to the top of the stack
 *
 * ## Note
 * Since element keys are required to be distinct in the routing stack, an element with the same key will be removed
 * from the stack before pushing the new element to the top
 */
@RoutingStackInstructionMarker
infix fun <T : Route, R> PlainStackInstructionSyntax<T, R>.push(element: Element<T>): R = plainStackInstruction {
    filterNot { it.key == element.key } + element
}

/**
 * Will remove all routes from the stack
 */
@RoutingStackInstructionMarker
fun <T : Route, R> PlainStackInstructionSyntax<T, R>.clear(): R = plainStackInstruction {
    emptyList()
}

/**
 * Will pop the top/active route if the routing stack is not empty
 */
@RoutingStackInstructionMarker
fun <T : Route, R> PlainStackInstructionSyntax<T, R>.pop(): R = plainStackInstruction {
    if (isEmpty()) emptyList()
    else subList(0, lastIndex)
}


/**
 * Will pop all routes from the top until the condition is hit (while the element that fulfills the condition is
 * not popped)
 */
@RoutingStackInstructionMarker
inline infix fun <T : Route, R> PlainStackInstructionSyntax<T, R>.popUntil(
    crossinline predicate: (T) -> Boolean
): R = plainStackInstruction {
    if (isEmpty()) emptyList()
    else dropLastWhile { element -> !predicate(element.route) }
}

/**
 * Will pop all routes from the top until the specified [route], while the given [route] itself is not popped
 */
@RoutingStackInstructionMarker
infix fun <T : Route, R> PlainStackInstructionSyntax<T, R>.popUntilRoute(route: T): R {
    return popUntil { it == route }
}
