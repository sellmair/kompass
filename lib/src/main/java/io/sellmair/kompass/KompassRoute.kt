package io.sellmair.kompass

import android.app.Activity
import android.content.Intent
import android.view.View
import kotlin.reflect.KClass


internal typealias AndroidFragment = android.support.v4.app.Fragment

/*
################################################################################################
PUBLIC API
################################################################################################
*/

sealed class KompassRoute {
    internal class Intent(val intent: android.content.Intent) : KompassRoute()
    internal class Activity<T : android.app.Activity>(val clazz: KClass<T>) : KompassRoute()
    internal class Fragment(val fragment: AndroidFragment) : KompassRoute()
    internal class View<T : android.view.View>(val clazz: KClass<T>) : KompassRoute()

    companion object
}

/*
################################################################################################
PUBLIC PSEUDO CONSTRUCTORS
################################################################################################
*/

operator fun KompassRoute.Companion.invoke(intent: Intent): KompassRoute =
    KompassRoute.Intent(intent)


operator fun <T : Activity> KompassRoute.invoke(clazz: KClass<T>): KompassRoute =
    KompassRoute.Activity(clazz)

operator fun KompassRoute.Companion.invoke(fragment: AndroidFragment): KompassRoute =
    KompassRoute.Fragment(fragment)

operator fun <T : View> KompassRoute.Companion.invoke(clazz: KClass<T>): KompassRoute =
    KompassRoute.View(clazz)


/*
################################################################################################
PUBLIC CONVENIENCE EXTENSIONS
################################################################################################
*/

fun AndroidFragment.asRoute() = KompassRoute(this)

@JvmName("asActivityRoute")
fun <T : Activity> KClass<T>.asRoute(): KompassRoute = KompassRoute.Activity(this)

@JvmName("asViewRoute")
fun <T : View> KClass<T>.asRoute(): KompassRoute = KompassRoute(this)

fun Intent.asRoute(): KompassRoute = KompassRoute(this)