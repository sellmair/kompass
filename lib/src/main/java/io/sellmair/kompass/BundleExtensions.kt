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


fun Bundle.getBooleanOrNull(key: String): Boolean? {
    if (this.containsKey(key)) return null
    return this.get(key) as Boolean
}

fun Bundle.getByteOrNull(key: String): Byte? {
    if (this.containsKey(key)) return null
    return this.get(key) as Byte
}

fun Bundle.getCharOrNull(key: String): Char? {
    if (this.containsKey(key)) return null
    return this.get(key) as Char
}

fun Bundle.getShortOrNull(key: String): Short? {
    if (this.containsKey(key)) return null
    return this.get(key) as Short
}

fun Bundle.getIntOrNull(key: String): Int? {
    if (this.containsKey(key)) return null
    return this.get(key) as Int
}

fun Bundle.getLongOrNull(key: String): Long? {
    if (this.containsKey(key)) return null
    return this.get(key) as Long
}

fun Bundle.getFloatOrNull(key: String): Float? {
    if (!this.containsKey(key)) return null
    return this.get(key) as Float
}

fun Bundle.getDoubleOrNull(key: String): Double? {
    if (this.containsKey(key)) return null
    return this.get(key) as Double
}


fun Bundle.getBooleanList(key: String): List<Boolean>? {
    return this.getBooleanArray(key)?.toList()
}

fun Bundle.getByteList(key: String): List<Byte>? {
    return this.getByteArray(key)?.toList()
}

fun Bundle.getCharList(key: String): List<Char>? {
    return this.getCharArray(key)?.toList()
}

fun Bundle.getShortList(key: String): List<Short>? {
    return this.getShortArray(key)?.toList()
}

fun Bundle.getIntList(key: String): List<Int>? {
    return this.getIntegerArrayList(key)
}

fun Bundle.getLongList(key: String): List<Long>? {
    return this.getLongArray(key)?.toList()
}

fun Bundle.getFloatList(key: String): List<Float>? {
    return this.getFloatArray(key)?.toList()
}

fun Bundle.getDoubleList(key: String): List<Double>? {
    return this.getDoubleArray(key)?.toList()
}

fun Bundle.getStringList(key: String): List<String>? {
    return this.getStringArrayList(key)
}

fun <T : Parcelable> Bundle.getParcelableList(key: String): List<T>? {
    return this.getParcelableArrayList(key)
}


private fun <T> List<T>.toArrayList(): ArrayList<T> {
    val arrayList = ArrayList<T>(this.size)
    arrayList.addAll(this)
    return arrayList
}

