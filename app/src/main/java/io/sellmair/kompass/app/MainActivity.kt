package io.sellmair.kompass.app

import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import io.sellmair.kompass.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.activity_main)
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

object KompassHolder {
    val kompass: Kompass<Destination> by lazy {
        Kompass.builder<Destination>()
            .build()
    }

    val main by lazy {
        kompass["main"]
    }
}