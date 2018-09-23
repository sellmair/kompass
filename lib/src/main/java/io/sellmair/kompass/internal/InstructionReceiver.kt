package io.sellmair.kompass.internal

import io.sellmair.kompass.internal.pipe.Instruction

/*
################################################################################################
INTERNAL API
################################################################################################
*/

internal interface InstructionReceiver<Destination : Any> {
    fun receive(instruction: Instruction<Destination>)
}