package io.sellmair.kompass

/**
 * Created by sebastiansellmair on 02.01.18.
 */
internal interface SailReference {
    fun get(): KompassSail?
    fun set(sail: KompassSail?)
    fun release()
}