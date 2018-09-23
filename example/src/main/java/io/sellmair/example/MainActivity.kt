package io.sellmair.example

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import io.sellmair.example.viewmodel.MainViewModel
import io.sellmair.kompass.extension.main
import io.sellmair.kompass.extension.sail

class MainActivity : AppCompatActivity() {

    private val kompass = DummyDependencyHolder.getKompass()
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProviders.of(this)[MainViewModel::class.java]
    }

    override fun onStart() {
        super.onStart()
        kompass.main.setSail(sail(R.id.container)).releasedBy(this)
    }

    override fun onBackPressed() {
        if (!kompass.backImmediate()) finish()
    }

}
