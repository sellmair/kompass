package io.sellmair.kompass.internal.pipe

import io.sellmair.kompass.KompassMap
import io.sellmair.kompass.KompassSelfMapped

internal class InstructionRouter<Destination : Any>(
    private val map: KompassMap<Destination>) :
    InstructionPipe<Payload<Destination, Stage.Sailed>, Payload<Destination, Stage.Routed>>,
    Handleable<Payload<Destination, Stage.Routed>> by Handleable.delegate() {

    override fun invoke(payload: Payload<Destination, Stage.Sailed>) {
        val destination = payload.instruction.destination
        val route = when (destination) {
            is KompassSelfMapped -> destination.route()
            else -> map[destination] ?: throwNoRouteFound(destination)
        }
        handle(payload.routed(route))
    }

    private fun throwNoRouteFound(destination: Destination): Nothing {
        throw KompassNoRouteException(destination)
    }

}


private class KompassNoRouteException(destination: Any) : Exception(
    """
        No route registered for destination ${destination.javaClass.simpleName} ($destination).
        Possible Solutions:

        - Implement a KompassMap and handle the given destination.
          Then supply this map to the KompassBuilder

        - Annotate the given destination with @Destination and specify a target

        Hopefully this helped you!
        If not, please make an issue at https://github.com/sellmair/kompass
        """.trimMargin().trim()
)