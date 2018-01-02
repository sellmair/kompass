package io.sellmair.kompass

import android.os.Bundle

/**
 * Created by sebastiansellmair on 02.01.18.
 */
interface KompassCrane<in Destination : Any> {
    fun bundle(destination: Destination): Bundle
}

