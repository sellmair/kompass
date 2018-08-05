package io.sellmair.kompass.internal.pipe.instruction

internal sealed class Instruction<Destination>(val destination: Destination) {
    class StartAt<Destination>(destination: Destination) : Instruction<Destination>(destination)
    class NavigateTo<Destination>(destination: Destination) : Instruction<Destination>(destination)
    class BeamTo<Destination>(destination: Destination) : Instruction<Destination>(destination)
}