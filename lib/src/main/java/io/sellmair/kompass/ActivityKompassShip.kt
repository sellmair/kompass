package io.sellmair.kompass

import android.app.Activity
import android.content.Intent
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import kotlin.reflect.KClass

/**
 * Created by sebastiansellmair on 06.12.17.
 */
internal class ActivityKompassShip(private val activity: FragmentActivity, containerId: Int) : AbstractKompassShip() {

    override fun navigateTo(intent: Intent) {
        activity.startActivity(intent)
    }

    override fun <T : Activity> navigateTo(activityClass: KClass<T>) {

    }

    override fun navigateTo(fragment: Fragment) {
        activity.supportFragmentManager
                .beginTransaction()

    }

}