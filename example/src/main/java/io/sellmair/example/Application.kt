package io.sellmair.example

import android.app.Application
import io.sellmair.example.destination.AppDestination
import io.sellmair.kompass.Kompass
import io.sellmair.kompass.autoCrane
import io.sellmair.kompass.autoMap
import io.sellmair.kompass.autoPilot

/**
 * Created by sebastiansellmair on 27.01.18.
 */

class Application : Application() {
    override fun onCreate() {
        super.onCreate()

        val kompass = Kompass.builder<AppDestination>(this)
                .autoCrane()
                .autoMap()
                .autoPilot()
                .build()

        DummyDependencyHolder.setKompass(kompass)
    }
}
