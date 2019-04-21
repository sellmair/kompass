package io.sellmair.kompass.core

typealias RouterInstruction<T> = RoutingStack<T>.() -> RoutingStack<T>

interface RouterInstructionSyntax<T : Route> {
    fun instruction(instruction: RouterInstruction<T>)
}


operator fun <T : Route> RouterInstruction<T>.plus(
    other: RouterInstruction<T>
): RouterInstruction<T> {
    return {
        this@plus.invoke(this)
        other.invoke(this)
    }
}

@Suppress("FunctionName")
fun <T : Route> EmptyRouterInstruction(): RouterInstruction<T> = { this }