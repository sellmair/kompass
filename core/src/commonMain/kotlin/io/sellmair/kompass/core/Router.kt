package io.sellmair.kompass.core


interface Router<T : Route> :
    RouterInstructionSyntax<T>,
    RoutingStackInstructionSyntax<T, Unit> {

    operator fun invoke(instruction: RouterInstruction<T>): Unit = instruction(instruction)

    override fun stackInstruction(instruction: List<RoutingStack.Element<T>>.() -> List<RoutingStack.Element<T>>): Unit =
        instruction { stackInstruction(instruction) }

}

