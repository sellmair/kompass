package io.sellmair.kompass.util

import io.sellmair.kompass.KompassSail

/**
 * Created by sebastiansellmair on 02.01.18.
 *
 * A Sail reference holds a weak reference to a [KompassSail].
 * The reference can also be released via [release]:
 * Using [get] afterwards won't return any instance afterwards.
 */
internal interface SailReference {
    /**
     * Will return the last [set] [KompassSail] unless [release] was called before,
     * or the sail got garbage collected.
     */
    fun get(): KompassSail?

    /**
     * Set's the new instance of the [KompassSail]
     * This instance will be retrievable by [get] until [release] is called,
     * or the object got garbage collected.
     */
    fun set(sail: KompassSail?)


    /**
     * Will remove any previously [set] reference.
     * The [get] will return 'null' afterwards.
     */
    fun release()
}