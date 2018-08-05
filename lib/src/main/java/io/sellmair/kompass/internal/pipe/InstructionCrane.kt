package io.sellmair.kompass.internal.pipe

import io.sellmair.kompass.KompassCrane
import io.sellmair.kompass.internal.pipe.instruction.BundledRoutedSailedInstruction
import io.sellmair.kompass.internal.pipe.instruction.RoutedSailedInstruction

internal class InstructionCrane<Destination : Any>(
    private val crane: KompassCrane<Destination>) :
    InstructionPipe<
        RoutedSailedInstruction<Destination>,
        BundledRoutedSailedInstruction<Destination>>,
    Handleable<BundledRoutedSailedInstruction<Destination>> by Handleable.delegate() {

    override fun invoke(instruction: RoutedSailedInstruction<Destination>) {
        val destination = instruction.instruction.destination
        val bundle = crane[destination] ?: throwNotCraned(destination)
        handle(BundledRoutedSailedInstruction(
            bundle, instruction.route, instruction.sail, instruction.instruction))
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