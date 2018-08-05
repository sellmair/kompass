package io.sellmair.kompass

import android.app.Activity
import android.content.Intent
import android.support.v4.app.Fragment
import android.view.View
import kotlin.reflect.KClass

sealed class KompassRoute {
    internal class Intent(val intent: android.content.Intent) : KompassRoute()
    internal class Activity<T : android.app.Activity>(val clazz: KClass<T>) : KompassRoute()
    internal class Fragment(val fragment: android.support.v4.app.Fragment) : KompassRoute()
    internal class View(val view: android.view.View) : KompassRoute()

    companion object
}


operator fun KompassRoute.Companion.invoke(intent: Intent): KompassRoute =
    KompassRoute.Intent(intent)


operator fun <T : Activity> KompassRoute.invoke(clazz: KClass<T>): KompassRoute =
    KompassRoute.Activity(clazz)

operator fun KompassRoute.Companion.invoke(fragment: Fragment): KompassRoute =
    KompassRoute.Fragment(fragment)

operator fun KompassRoute.Companion.invoke(view: View): KompassRoute =
    KompassRoute.View(view)

