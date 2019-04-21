package io.sellmair.kompass.core


interface Router<T : Route> {
    fun execute(instruction: RoutingStackInstruction<T>)
}

