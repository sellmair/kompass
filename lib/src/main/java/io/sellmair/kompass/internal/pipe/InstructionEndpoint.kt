package io.sellmair.kompass.internal.pipe

internal interface InstructionEndpoint<Destination : Any> :
    InstructionPipe<Payload<Destination, Stage.Routed>, Unit>