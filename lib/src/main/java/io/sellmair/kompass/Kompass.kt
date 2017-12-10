package io.sellmair.kompass

import android.content.Intent
import android.os.Bundle
import kotlin.reflect.KClass


/**
 * Created by sebastiansellmair on 06.12.17.
 */
interface Kompass<in Destination : KompassDestination> {

    fun setShip(ship: KompassShip)
    fun <T : Destination> navigateTo(destination: T, replaceCurrent: Boolean = false)
    fun <T : Destination> getDestination(clazz: KClass<T>, bundle: Bundle): T
    fun <T : Destination> getDestination(clazz: KClass<T>, intent: Intent): T = getDestination(clazz, intent.extras)

    fun exit()

    companion object {
        fun <Destination : KompassDestination> create(map: KompassMap<Destination>)
                : Kompass<Destination> = BaseKompass(map)
    }
}