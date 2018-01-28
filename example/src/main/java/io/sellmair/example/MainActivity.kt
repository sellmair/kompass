package io.sellmair.example

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import io.sellmair.example.extension.main
import io.sellmair.example.viewmodel.MainViewModel
import io.sellmair.kompass.KompassSail

class MainActivity : AppCompatActivity() {

    private val kompass = DummyDependencyHolder.getKompass()
    private lateinit var sail: KompassSail
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProviders.of(this)[MainViewModel::class.java]
    }

    override fun onResume() {
        super.onResume()
        this.sail = kompass.main.setSail(this, R.id.container)
    }

    override fun onPause() {
        super.onPause()
        this.sail.release()
    }


    override fun onBackPressed() {
        if (!kompass.popBackImmediate()) finish()
    }

}
