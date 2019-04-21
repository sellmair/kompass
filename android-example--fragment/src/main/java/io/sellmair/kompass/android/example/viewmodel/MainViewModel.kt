package io.sellmair.kompass.android.example.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import io.sellmair.kompass.android.example.ContactListRoute
import io.sellmair.kompass.android.example.Dependencies
import io.sellmair.kompass.android.example.DummyService
import io.sellmair.kompass.android.example.LoginRoute
import io.sellmair.kompass.core.clear
import io.sellmair.kompass.core.push

/**
 * Created by sebastiansellmair on 27.01.18.
 */
class MainViewModel : ViewModel() {
    private val router = Dependencies.router

    fun checkIfLoggedIn() {
        Log.d("Example", "checkIfLoggedIn")
        val route = if (!DummyService.isLoggedIn) {
            LoginRoute()
        } else {
            ContactListRoute(
                searchString = null,
                contacts = DummyService.contacts
            )
        }

        router.execute {
            clear().push(route)
        }
    }


}




