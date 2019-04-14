package io.sellmair.kompass.android.example

import android.app.Application
import io.sellmair.kompass.android.example.fragment.*
import io.sellmair.kompass.android.example.transitions.ContactListToChatTransition
import io.sellmair.kompass.android.example.transitions.LoginProcessingToContactListTransition
import io.sellmair.kompass.android.example.transitions.LoginProcessingToLoginFailedTransition
import io.sellmair.kompass.android.example.transitions.LoginToLoginProcessingTransition
import io.sellmair.kompass.android.fragment.FragmentRouter

/**
 * Created by sebastiansellmair on 27.01.18.
 */

class Application : Application() {
    override fun onCreate() {
        super.onCreate()
        Dependencies.router = FragmentRouter {
            animation {
                register(LoginToLoginProcessingTransition())
                register(ContactListToChatTransition())
                register(LoginProcessingToLoginFailedTransition())
                register(LoginProcessingToContactListTransition())
            }
            routing {
                route<LoginRoute> { LoginFragment::class }
                route<LoginProcessingRoute> { LoginProcessingFragment::class }
                route<ContactListRoute> { ContactListFragment::class }
                route<ChatRoute> { ChatFragment::class }
                route<LoginFailedRoute> { LoginFailedFragment::class }
            }
        }
    }
}
