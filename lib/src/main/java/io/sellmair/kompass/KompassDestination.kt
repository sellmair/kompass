package io.sellmair.kompass

import android.os.Bundle

/**
 * Created by sebastiansellmair on 06.12.17.
 */
interface KompassDestination {
    fun writeToBundle(bundle: Bundle)
}