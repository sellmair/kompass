package io.sellmair.example.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import io.sellmair.example.DummyDependencyHolder
import io.sellmair.example.destination.LoginProcessingDestination
import io.sellmair.example.extension.main

/**
 * Created by sebastiansellmair on 27.01.18.
 */
class LoginViewModel : ViewModel() {
    private val kompass = DummyDependencyHolder.getKompass()

    var email: String? = null
    var password: String? = null
    var alertText = MutableLiveData<String>()

    fun onLoginClicked() {
        val email = this.email
        val password = this.password
        if (email == null || password == null) {
            alertText.value = "Please enter email and password"
            return
        }

        kompass.main navigateTo LoginProcessingDestination(email, password)
    }

}