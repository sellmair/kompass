package io.sellmair.kompass.util

import io.sellmair.kompass.KompassSail

/**
 * Created by sebastiansellmair on 02.01.18.
 */
internal interface SailReference {
    fun get(): KompassSail?
    fun set(sail: KompassSail?)
    fun release()
}