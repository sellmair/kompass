package io.sellmair.kompass

import android.os.Bundle
import io.sellmair.kompass.exception.MissingBundleSerializerException
import io.sellmair.kompass.util.tryOrNull

internal class CompositeKompassCrane<in Destination : Any>
(private val cranes: List<KompassCrane<Destination>>) : KompassCrane<Destination> {
    override fun bundle(destination: Destination): Bundle {
        return cranes.asSequence()
                .mapNotNull { crane -> tryOrNull { crane.bundle(destination) } }
                .firstOrNull() ?: throw MissingBundleSerializerException(destination)
    }

}