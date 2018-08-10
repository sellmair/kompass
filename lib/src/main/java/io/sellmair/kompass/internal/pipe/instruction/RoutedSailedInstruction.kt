package io.sellmair.kompass.internal.pipe.instruction

import io.sellmair.kompass.KompassRoute
import io.sellmair.kompass.KompassSail

internal class RoutedSailedInstruction<Destination : Any>(
    val route: KompassRoute,
    val sail: KompassSail,
    val instruction: Instruction<Destination>)