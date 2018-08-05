package io.sellmair.example.viewmodel

import android.arch.lifecycle.ViewModel
import io.sellmair.example.DummyDependencyHolder
import io.sellmair.example.DummyService
import io.sellmair.example.destination.ContactListDestination
import io.sellmair.example.destination.LoginDestination

/**
 * Created by sebastiansellmair on 27.01.18.
 */
class MainViewModel : ViewModel() {
    private val kompass = DummyDependencyHolder.getKompass()

    private fun checkIfLoggedIn() {
        val destination = if (!DummyService.isLoggedIn) {
            LoginDestination()
        } else {
            ContactListDestination(searchString = null, contacts = DummyService.contacts)
        }

        kompass.main startAt destination
    }

    init {
        checkIfLoggedIn()
    }
}




