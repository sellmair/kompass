package io.sellmair.example

import io.sellmair.example.destination.AppDestination
import io.sellmair.kompass.Kompass

/**
 * Created by sebastiansellmair on 27.01.18.
 */
class DummyDependencyHolder {

    companion object {
        private var kompassInstance: Kompass<AppDestination>? = null

        fun setKompass(kompass: Kompass<AppDestination>) {
            DummyDependencyHolder.kompassInstance = kompass
        }

        fun getKompass(): Kompass<AppDestination> {
            return kompassInstance ?: throw IllegalStateException("Kompass not set")
        }
    }
}