package io.sellmair.kompass.app

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import io.sellmair.kompass.asJuliansDestinatino


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        intent.extras.asJuliansDestinatino()?.ids
    }
}
