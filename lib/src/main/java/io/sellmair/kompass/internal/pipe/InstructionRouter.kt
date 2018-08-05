package io.sellmair.kompass.internal.pipe

import io.sellmair.kompass.KompassMap
import io.sellmair.kompass.internal.pipe.instruction.RoutedSailedInstruction
import io.sellmair.kompass.internal.pipe.instruction.SailedInstruction

internal class InstructionRouter<Destination : Any>(
    private val map: KompassMap<Destination>) :
    InstructionPipe<SailedInstruction<Destination>, RoutedSailedInstruction<Destination>>,
    Handleable<RoutedSailedInstruction<Destination>> by Handleable.delegate() {

    override fun invoke(instruction: SailedInstruction<Destination>) {
        val destination = instruction.instruction.destination
        val route = map[destination] ?: throwNoRouteFound(destination)
        handle(RoutedSailedInstruction(route, instruction.sail, instruction.instruction))
    }

    private fun throwNoRouteFound(destination: Destination): Nothing {
        throw KompassNoRouteException(destination)
    }

}


private class KompassNoRouteException(destination: Any) : Exception(
    message = """
        No route registered for destination ${destination.javaClass.simpleName} ($destination).
        Possible Solutions:

        - Implement a KompassMap and handle the given destination.
          Then supply this map to the KompassBuilder

        - Annotate the given destination with @Destination and specify a target

        Hopefully this helped you!
        If not, please make an issue at https://github.com/sellmair/kompass
        """.trimMargin().trim()
)