package io.sellmair.kompass.app

import io.sellmair.kompass.Kompass
import io.sellmair.kompass.builder

object Holder {
    val kompass: Kompass<Destination> by lazy {
        Kompass.builder<Destination>()
            .addCrane(Crane())
            .addMap(Map())
            .build()
    }

    val main by lazy {
        kompass["main"]
    }
}