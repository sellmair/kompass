package io.sellmair.kompass.internal.pipe.instruction

import io.sellmair.kompass.internal.pipe.InstructionPipe

internal interface InstructionEndpoint<Destination : Any> :
    InstructionPipe<BundledRoutedSailedInstruction<Destination>, Unit>