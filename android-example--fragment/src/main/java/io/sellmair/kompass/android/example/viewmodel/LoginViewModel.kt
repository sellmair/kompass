package io.sellmair.kompass.android.example.viewmodel


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.sellmair.kompass.android.example.Dependencies
import io.sellmair.kompass.android.example.LoginProcessingRoute
import io.sellmair.kompass.core.push

/**
 * Created by sebastiansellmair on 27.01.18.
 */
class LoginViewModel : ViewModel() {
    private val router = Dependencies.router

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

        router.push(LoginProcessingRoute(email, password))
    }

}