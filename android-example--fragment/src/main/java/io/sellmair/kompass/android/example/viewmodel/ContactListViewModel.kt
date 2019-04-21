package io.sellmair.kompass.android.example.viewmodel

import androidx.lifecycle.ViewModel
import io.sellmair.kompass.android.example.ChatRoute
import io.sellmair.kompass.android.example.Contact
import io.sellmair.kompass.android.example.Dependencies
import io.sellmair.kompass.core.push


/**
 * Created by sebastiansellmair on 28.01.18.
 */
class ContactListViewModel : ViewModel() {

    private val router = Dependencies.router

    fun onContactClicked(contact: Contact) {
        router push ChatRoute(
            lastSeenTime = System.currentTimeMillis(),
            backgroundId = 1,
            chatTitle = contact.nickname ?: contact.name,
            savedAlreadyTypedText = "",
            contact = contact
        )
    }
}