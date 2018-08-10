package io.sellmair.kompass.internal.pipe.instruction

internal sealed class Instruction<Destination : Any>(val destination: Destination) {
    class StartAt<Destination : Any>(destination: Destination) :
        Instruction<Destination>(destination)

    class NavigateTo<Destination : Any>(destination: Destination) :
        Instruction<Destination>(destination)

    class BeamTo<Destination : Any>(destination: Destination) :
        Instruction<Destination>(destination)
}