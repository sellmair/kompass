package io.sellmair.kompass.app

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import io.sellmair.kompass.Kompass
import io.sellmair.kompass.autoCrane
import io.sellmair.kompass.autoMap


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val kompass = Kompass.builder<Any>(this)
                .autoMap()
                .autoCrane()
                .build()


        kompass["lisa"].navigateTo(JuliansDestination("", emptyList()))
        kompass["lisa"].setSail(this, R.id.action_bar_container)
    }

}
