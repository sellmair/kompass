package io.sellmair.kompass

import android.os.Bundle
import android.os.Parcelable


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

fun Bundle.putBooleanList(key: String, value: List<Boolean>?) {
    if (value == null) {
        this.remove(key)
        return
    }

    this.putBooleanArray(key, value.toBooleanArray())
}

fun Bundle.putByteList(key: String, value: List<Byte>?) {
    if (value == null) {
        this.remove(key)
        return
    }

    this.putByteArray(key, value.toByteArray())
}

fun Bundle.putCharList(key: String, value: List<Char>?) {
    if (value == null) {
        this.remove(key)
        return
    }

    this.putCharArray(key, value.toCharArray())
}

fun Bundle.putShortList(key: String, value: List<Short>?) {
    if (value == null) {
        this.remove(key)
        return
    }

    this.putShortArray(key, value.toShortArray())
}

fun Bundle.putIntList(key: String, value: List<Int>?) {
    if (value == null) {
        this.remove(key)
        return
    }

    this.putIntegerArrayList(key, value.toArrayList())
}

fun Bundle.putLongList(key: String, value: List<Long>?) {
    if (value == null) {
        this.remove(key)
        return
    }

    this.putLongArray(key, value.toLongArray())
}

fun Bundle.putFloatList(key: String, value: List<Float>?) {
    if (value == null) {
        this.remove(key)
        return
    }

    this.putFloatArray(key, value.toFloatArray())
}


fun Bundle.putDoubleList(key: String, value: List<Double>?) {
    if (value == null) {
        this.remove(key)
        return
    }

    this.putDoubleArray(key, value.toDoubleArray())
}

fun Bundle.putStringList(key: String, value: List<String>?) {
    if (value == null) {
        this.remove(key)
        return
    }

    this.putStringArrayList(key, value.toArrayList())
}

fun <T : Parcelable> Bundle.putParcelableList(key: String, value: List<T>?) {
    if (value == null) {
        this.remove(key)
        return
    }

    this.putParcelableArrayList(key, value.toArrayList())
}

private fun <T> List<T>.toArrayList(): ArrayList<T> {
    val arrayList = ArrayList<T>(this.size)
    arrayList.addAll(this)
    return arrayList
}

