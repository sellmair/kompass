package io.sellmair.example.destination

import io.sellmair.example.Contact
import io.sellmair.example.fragment.*
import io.sellmair.kompass.annotation.Destination

/**
 * Created by sebastiansellmair on 27.01.18.
 */
sealed class AppDestination

@Destination(target = [LoginFragment::class])
class LoginDestination : AppDestination()


@Destination(target = [LoginProcessingFragment::class])
data class LoginProcessingDestination(
        val email: String,
        val password: String
) : AppDestination()


@Destination(target = [ContactListFragment::class])
data class ContactListDestination(
    val searchString: String?,
    val contacts: List<Contact>) : AppDestination()


@Destination(target = [ChatFragment::class])
data class ChatDestination(
    val lastSeenTime: Long,
    val backgroundId: Int,
    val chatTitle: String,
    val savedAlreadyTypedText: String,
    val contact: Contact) : AppDestination()


@Destination(target = [LoginFailedFragment::class])
data class LoginFailedDestination(val username: String)
    : AppDestination()
