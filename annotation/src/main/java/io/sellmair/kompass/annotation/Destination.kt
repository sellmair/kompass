package io.sellmair.kompass.annotation

import kotlin.reflect.KClass


/**
 * Created by sebastiansellmair on 09.12.17.
 */
@Retention(AnnotationRetention.SOURCE)
@Target(AnnotationTarget.CLASS)
annotation class Destination(val target: Array<KClass<*>> = [])

