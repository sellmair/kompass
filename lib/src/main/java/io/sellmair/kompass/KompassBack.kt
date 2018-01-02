package io.sellmair.kompass

/**
 * Created by sebastiansellmair on 02.01.18.
 */

interface KompassBack {
    val key: Any?
    val keySingleton: Boolean
    fun back(): Boolean
}