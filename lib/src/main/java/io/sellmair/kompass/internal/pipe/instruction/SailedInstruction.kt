package io.sellmair.kompass.internal.pipe.instruction

import io.sellmair.kompass.KompassSail

internal class SailedInstruction<Destination : Any>(
    val sail: KompassSail,
    val instruction: Instruction<Destination>)