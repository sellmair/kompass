package io.sellmair.kompass.android.example

import android.os.Parcelable
import io.sellmair.kompass.core.Route
import kotlinx.android.parcel.Parcelize

/**
 * Created by sebastiansellmair on 27.01.18.
 */
sealed class AppRoute : Route, Parcelable

@Parcelize
class LoginRoute : AppRoute()


@Parcelize
data class LoginProcessingRoute(
    val email: String,
    val password: String
) : AppRoute()


@Parcelize
data class ContactListRoute(
    val searchString: String?,
    val contacts: List<Contact>
) : AppRoute()

@Parcelize
data class ChatRoute(
    val lastSeenTime: Long,
    val backgroundId: Int,
    val chatTitle: String,
    val savedAlreadyTypedText: String,
    val contact: Contact
) : AppRoute()


@Parcelize
data class LoginFailedRoute(val username: String) : AppRoute()