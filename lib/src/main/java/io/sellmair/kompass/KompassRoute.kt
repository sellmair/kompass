@file:Suppress("FunctionName")

package io.sellmair.kompass

import android.app.Activity
import android.content.Intent
import android.support.v4.app.Fragment
import kotlin.reflect.KClass

/**
 * Created by sebastiansellmair on 06.12.17.
 */
sealed class KompassRoute

internal class IntentKompassRoute(val intent: Intent) : KompassRoute()
internal class ActivityKompassRoute<T : Activity>(val activityClass: KClass<T>) : KompassRoute()
internal class FragmentKompassRoute(val fragment: Fragment) : KompassRoute()

fun KompassRoute(intent: Intent): KompassRoute = IntentKompassRoute(intent)
fun <T : Activity> KompassRoute(activityClass: KClass<T>): KompassRoute = ActivityKompassRoute(activityClass)
fun KompassRoute(fragment: Fragment): KompassRoute = FragmentKompassRoute(fragment)

fun <T : Activity> KClass<T>.toRoute(): KompassRoute = KompassRoute(this)
fun Intent.toRoute(): KompassRoute = KompassRoute(this)
fun Fragment.toRoute(): KompassRoute = KompassRoute(this)


