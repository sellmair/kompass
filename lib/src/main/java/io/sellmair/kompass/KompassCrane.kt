package io.sellmair.kompass

import android.os.Bundle
import android.support.annotation.AnyThread

interface KompassCrane<in Destination> {
    @AnyThread
    operator fun get(destination: Destination): Bundle?
}