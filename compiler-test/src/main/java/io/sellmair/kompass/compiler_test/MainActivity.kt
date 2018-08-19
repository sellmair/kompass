package io.sellmair.kompass.compiler_test

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import io.sellmair.kompass.Kompass
import io.sellmair.kompass.autoCrane
import io.sellmair.kompass.builder

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Kompass.builder<Any>()
            .autoCrane()

    }
}
