package io.sellmair.kompass.internal.pipe

import io.sellmair.kompass.KompassCrane

internal class InstructionCrane<Destination : Any>(
    private val crane: KompassCrane<Destination>) :
    InstructionPipe<Payload<Destination, Stage.Pending>, Payload<Destination, Stage.Craned>>,
    Handleable<Payload<Destination, Stage.Craned>> by Handleable.delegate() {

    override fun invoke(payload: Payload<Destination, Stage.Pending>) {
        val destination = payload.instruction.destination
        val bundle = crane[destination] ?: throwNotCraned(destination)
        handle(payload.craned(bundle))
    }

    private fun throwNotCraned(destination: Destination): Nothing {
        throw KompassNotCranedException(destination)
    }

}

private class KompassNotCranedException(destination: Any) : Exception(
    """
    No suitable crane registered for ${destination.javaClass.simpleName} ($destination).
    Possible Solutions:

    - Implement a KompassCrane and handle the given destination.
      Then supply this crane to the KompassBuilder

    - Annotate the given destination with @Destination

    Hopefully this helped you!
    If not, please make an issue at https://github.com/sellmair/kompass
    """.trimMargin().trim())