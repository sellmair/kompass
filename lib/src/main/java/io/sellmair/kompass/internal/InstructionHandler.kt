package io.sellmair.kompass.internal

import android.os.Bundle
import io.sellmair.kompass.KompassRoute
import io.sellmair.kompass.KompassSail
import io.sellmair.kompass.internal.pipe.instruction.Instruction

internal interface InstructionHandler<Destination> {
    operator fun invoke(sail: KompassSail, instruction: Instruction<Destination>): Boolean
}

internal interface Pusher {
    operator fun invoke(sail: KompassSail, arguments: Bundle, route: KompassRoute)
}

