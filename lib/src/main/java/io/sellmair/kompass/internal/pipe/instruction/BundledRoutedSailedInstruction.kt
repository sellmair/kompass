package io.sellmair.kompass.internal.pipe.instruction

import android.os.Bundle
import io.sellmair.kompass.KompassRoute
import io.sellmair.kompass.KompassSail

internal open class BundledRoutedSailedInstruction<Destination : Any>(
    val bundle: Bundle,
    val route: KompassRoute,
    val sail: KompassSail,
    val instruction: Instruction<Destination>)