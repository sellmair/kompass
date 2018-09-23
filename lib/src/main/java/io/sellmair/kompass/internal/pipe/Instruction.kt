package io.sellmair.kompass.internal.pipe

internal sealed class Instruction<Destination : Any>(val destination: Destination) {
    class StartAt<Destination : Any>(destination: Destination) :
        Instruction<Destination>(destination)

    class NavigateTo<Destination : Any>(destination: Destination) :
        Instruction<Destination>(destination)

    class BeamTo<Destination : Any>(destination: Destination) :
        Instruction<Destination>(destination)

    /**
     * Generic instruction that can be used to handle multiple actions.
     * The main purpose of this instruction is re-flowing through the buffer / pipeline.
     *
     * A good example would be [FragmentEndpoint.popBackStackImmediate] which uses
     * this instruction to receive the latest fragment manager to pop back!
     */
    abstract class Generic<Destination : Any>(destination: Destination) :
        Instruction<Destination>(destination)
}