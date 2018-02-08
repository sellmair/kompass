package io.sellmair.example.viewmodel

import android.arch.lifecycle.ViewModel
import android.os.Handler
import android.os.Looper
import io.sellmair.example.DummyDependencyHolder
import io.sellmair.example.DummyService
import io.sellmair.example.destination.ContactListDestination
import io.sellmair.example.destination.LoginFailedDestination
import io.sellmair.example.extension.main

/**
 * Created by sebastiansellmair on 28.01.18.
 */
class LoginProcessingViewModel : ViewModel() {
    private val kompass = DummyDependencyHolder.getKompass()
    private val handler = Handler(Looper.getMainLooper())
    private lateinit var email: String
    private lateinit var password: String

    private val loggedIn = Runnable {

        if (password == "kompass") {
            DummyService.isLoggedIn = true
            kompass.main *= ContactListDestination(null, DummyService.contacts)
        } else {
            kompass.main %= LoginFailedDestination(email)
        }
    }

    fun setCredentials(email: String, password: String) {
        this.email = email
        this.password = password
        handler.postDelayed(loggedIn, 3000)
    }

    fun stop() {
        handler.removeCallbacks(loggedIn)
    }


    override fun onCleared() {
        super.onCleared()
        handler.removeCallbacks(loggedIn)
    }
}