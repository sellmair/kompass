package io.sellmair.kompass.android

import android.os.Parcelable

interface Parcelizer<T> {
    fun toParcelable(value: T): Parcelable
    fun fromParcelable(parcelable: Parcelable): T
}