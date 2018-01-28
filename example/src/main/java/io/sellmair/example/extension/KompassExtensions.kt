package io.sellmair.example.extension

import io.sellmair.example.destination.AppDestination
import io.sellmair.kompass.Kompass
import io.sellmair.kompass.KompassShip

/**
 * Created by sebastiansellmair on 27.01.18.
 */
val Kompass<AppDestination>.main: KompassShip<AppDestination> get() = this["main"]