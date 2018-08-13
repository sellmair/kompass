package io.sellmair.kompass

import android.os.Bundle
import android.os.Parcelable

fun Bundle.putStringList(key: String, list: List<String>?) {
    if (list == null) {
        this.putStringArrayList(key, null)
        return
    }

    this.putStringArrayList(key, list.toArrayList())
}

fun Bundle.putParcelableList(key: String, list: List<Parcelable>?) {
    if (list == null) {
        this.putParcelableArrayList(key, list)
        return
    }

    this.putParcelableArrayList(key, list.toArrayList())
}

fun Bundle.getStringList(key: String): List<String>? {
    return this.getStringArrayList(key)
}

fun <T : Parcelable> Bundle.getParcelableList(key: String): List<T> {
    return this.getParcelableArrayList(key)
}

fun Bundle.getIntOrNull(key: String): Int? {
    if (!this.containsKey(key)) return null
    return this.get(key) as Int
}

fun Bundle.getFloatOrNull(key: String): Float? {
    if (!this.containsKey(key)) return null
    return this.get(key) as Float
}

private fun <T> List<T>.toArrayList(): ArrayList<T> {
    val arrayList = ArrayList<T>(this.size)
    arrayList.addAll(this)
    return arrayList
}