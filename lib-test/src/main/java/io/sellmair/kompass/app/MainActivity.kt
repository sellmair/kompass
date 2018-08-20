package io.sellmair.kompass.app

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import io.sellmair.kompass.extension.sail

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()
        Holder.main.setSail(sail(R.id.container)).releasedBy(this)
    }

    override fun onBackPressed() {
        if (!Holder.kompass.backImmediate()) {
            finish()
        }
    }
}

