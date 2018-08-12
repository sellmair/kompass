package io.sellmair.kompass.internal.util

import java.lang.ref.SoftReference
import kotlin.reflect.KProperty


internal fun <T : Any> soft(value: T?): SoftReferenceDelegate<T> {
    return SoftReferenceDelegate(value)
}

internal inline fun <T : Any> soft(producer: () -> T?): SoftReferenceDelegate<T> {
    return SoftReferenceDelegate(producer())
}

class SoftReferenceDelegate<T : Any>(val value: T?) : SoftReference<T>(value) {
    operator fun getValue(thisRef: Any?, property: KProperty<*>): T? {
        return this.get()
    }
}
