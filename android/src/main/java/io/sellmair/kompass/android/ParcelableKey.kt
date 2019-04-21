package io.sellmair.kompass.android

import android.os.Parcelable
import io.sellmair.kompass.core.Key
import io.sellmair.kompass.core.randomKeyValue
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ParcelableKey(override val value: String = randomKeyValue()) : Key(),
    Parcelable

fun Key.parcelable(): ParcelableKey {
    return when (this) {
        is ParcelableKey -> this
        else -> ParcelableKey(value)
    }
}