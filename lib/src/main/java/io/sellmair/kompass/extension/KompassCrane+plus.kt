package io.sellmair.kompass.extension

import android.os.Bundle
import io.sellmair.kompass.KompassCrane

operator fun <Destination> KompassCrane<Destination>.plus(other: KompassCrane<Destination>)
    : KompassCrane<Destination> = KompassCraneConnector(this, other)


private class KompassCraneConnector<Destination>(
    private val first: KompassCrane<Destination>,
    private val second: KompassCrane<Destination>) :
    KompassCrane<Destination> {
    override fun get(destination: Destination): Bundle? {
        return this.first[destination] ?: second[destination]
    }
}