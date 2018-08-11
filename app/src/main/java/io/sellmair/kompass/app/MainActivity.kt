package io.sellmair.kompass.app

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import io.sellmair.kompass.*
import io.sellmair.kompass.extension.sail

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        KompassHolder.main.startAt(Destination.One(1))
    }

    override fun onStart() {
        super.onStart()
        KompassHolder.main.setSail(sail(R.id.container)).releasedBy(this)
    }

    override fun onBackPressed() {
        if (!KompassHolder.kompass.backImmediate()) {
            super.onBackPressed()
        }
    }
}

sealed class Destination {
    class One(val id: Int) : Destination()
}

class Map : KompassMap<Destination> {
    override fun get(destination: Destination): KompassRoute? {
        return when (destination) {
            is Destination.One -> KompassRoute(FragmentOne())
        }
    }
}

class Crane : KompassCrane<Destination> {
    override fun get(destination: Destination) = when (destination) {
        is Destination.One -> Bundle().apply {
            putInt("id", destination.id)
        }
    }
}

object KompassHolder {
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