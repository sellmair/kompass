package io.sellmair.kompass.app

import android.os.Bundle
import io.sellmair.kompass.KompassCrane

class Crane : KompassCrane<Destination> {
    override fun get(destination: Destination) = Bundle().apply {
        putString("key", destination.key)
    }
}