package io.sellmair.kompass

import android.app.Activity
import android.content.Intent
import android.support.v4.app.Fragment
import kotlin.reflect.KClass

/**
 * Created by sebastiansellmair on 06.12.17.
 */
internal abstract class AbstractKompassShip : KompassShip {
    override fun navigateTo(route: KompassRoute) = when (route) {
        is IntentKompassRoute -> navigateTo(route.intent)
        is ActivityKompassRoute<*> -> navigateTo(route.activityClass)
        is FragmentKompassRoute -> navigateTo(route.fragment)
    }

    protected abstract fun navigateTo(intent: Intent)
    protected abstract fun <T : Activity> navigateTo(activityClass: KClass<T>)
    protected abstract fun navigateTo(fragment: Fragment)
}