package io.sellmair.example.viewmodel

import android.arch.lifecycle.ViewModel
import io.sellmair.example.Contact
import io.sellmair.example.DummyDependencyHolder
import io.sellmair.example.destination.ChatDestination
import io.sellmair.example.extension.main

/**
 * Created by sebastiansellmair on 28.01.18.
 */
class ContactListViewModel : ViewModel() {

    private val kompass = DummyDependencyHolder.getKompass()

    fun onContactClicked(contact: Contact) {
        kompass.main.navigateTo(ChatDestination(
                System.currentTimeMillis(),
                1,
                contact.nickname ?: contact.name,
                savedAlreadyTypedText = "",
                contact = contact
        ))
    }
}