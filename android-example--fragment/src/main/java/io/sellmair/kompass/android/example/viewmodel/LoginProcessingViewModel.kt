package io.sellmair.kompass.android.example.viewmodel

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.ViewModel
import io.sellmair.kompass.android.example.ContactListRoute
import io.sellmair.kompass.android.example.Dependencies
import io.sellmair.kompass.android.example.DummyService
import io.sellmair.kompass.android.example.LoginFailedRoute
import io.sellmair.kompass.core.clear
import io.sellmair.kompass.core.pop
import io.sellmair.kompass.core.push

/**
 * Created by sebastiansellmair on 28.01.18.
 */
class LoginProcessingViewModel : ViewModel() {
    private val router = Dependencies.router
    private val handler = Handler(Looper.getMainLooper())
    private lateinit var email: String
    private lateinit var password: String

    private val loggedIn = Runnable {
        if (password == "kompass") {
            onLoginSuccess()
        } else {
            onLoginFailed()
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

    private fun onLoginFailed() = router {
        pop() push LoginFailedRoute(email)
    }

    private fun onLoginSuccess() = router {
        DummyService.isLoggedIn = true
        clear() push ContactListRoute(
            searchString = null,
            contacts = DummyService.contacts
        )
    }

    override fun onCleared() {
        super.onCleared()
        handler.removeCallbacks(loggedIn)
    }
}