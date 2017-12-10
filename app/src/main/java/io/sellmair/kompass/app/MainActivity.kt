package io.sellmair.kompass.app

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import io.sellmair.kompass.asExampleDestination
import io.sellmair.kompass.asYoloDestination
import io.sellmair.kompass.bundle.BundleAdapter

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val dest = ExampleDestination(100, 33, "yolo")
        val bundle = Bundle()
        ExampleDestinationSerializer().writeToBundle(dest, BundleAdapter(bundle))

        val newDest = bundle.asExampleDestination() ?: return
        val asYolo = bundle.asYoloDestination()
    }
}
