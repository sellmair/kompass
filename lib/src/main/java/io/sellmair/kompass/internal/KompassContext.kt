package io.sellmair.kompass.internal

import io.sellmair.kompass.KompassCrane
import io.sellmair.kompass.KompassDetour
import io.sellmair.kompass.KompassMap

internal interface KompassContext<Destination> {
    val map: KompassMap<Destination>
    val crane: KompassCrane<Destination>
    val detour: KompassDetour<Destination>
}