package io.sellmair.kompass.app

import android.os.Bundle
import android.support.v7.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val dest = ExampleDestination(100, 33, "yolo")
        val bundle = Bundle()
    }
}
