package io.sellmair.kompass.android.example

import android.app.Application
import android.util.Log
import io.sellmair.kompass.android.example.fragment.*
import io.sellmair.kompass.android.example.transitions.*
import io.sellmair.kompass.android.fragment.FragmentRouter

/**
 * Created by sebastiansellmair on 27.01.18.
 */

class Application : Application() {
    override fun onCreate() {
        super.onCreate()
        Log.d("Example", "Application.onCreate")

        Dependencies.router = FragmentRouter {
            transitions {
                register(LoginToLoginProcessingTransition())
                register(ContactListToChatTransition())
                register(LoginProcessingToLoginFailedTransition())
                register(LoginProcessingToContactListTransition())
                register(LoginFailedToLoginTransition)
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
