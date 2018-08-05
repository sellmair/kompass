package io.sellmair.kompass.internal.pipe.instruction

import io.sellmair.kompass.KompassSail

internal class SailedInstruction<Destination>(
    val sail: KompassSail,
    val instruction: Instruction<Destination>)