package io.sellmair.kompass

import android.os.Bundle
import android.support.annotation.AnyThread

/*
################################################################################################
PUBLIC API
################################################################################################
*/

interface KompassCrane<in Destination> {
    @AnyThread
    operator fun get(destination: Destination): Bundle?

    companion object
}

/*
################################################################################################
INTERNAL FACTORIES
################################################################################################
*/

internal fun <Destination> KompassCrane.Companion.empty(): KompassCrane<Destination> = object :
    KompassCrane<Destination> {
    override fun get(destination: Destination): Bundle? = null
}
