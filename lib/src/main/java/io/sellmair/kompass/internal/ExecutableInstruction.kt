package io.sellmair.kompass.internal

import io.sellmair.kompass.KompassSail
import io.sellmair.kompass.internal.pipe.instruction.Instruction

internal class ExecutableInstruction<Destination>(
    val sail: KompassSail,
    val instruction: Instruction<Destination>)