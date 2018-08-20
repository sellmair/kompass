package io.sellmair.kompass.internal

import kotlin.reflect.KClass

class UnsafeRegistry {

    private val entries = mutableListOf<Entry>()

    fun add(classes: Array<KClass<*>>, payload: Any) {
        val entry = Entry(classes, payload)
        entries.add(entry)
    }

    fun find(classes: Array<KClass<*>>): Any? {
        for (entry in entries) {
            if (entry.isAssignable(classes)) return entry.payload
        }

        return null
    }

    private fun Entry.isAssignable(classes: Array<KClass<*>>): Boolean {
        if (classes.size != this.classes.size) return false

        for (i in 0 until classes.size) {
            val clazz = classes[i]
            val thisClazz = this.classes[i]

            if (!thisClazz.java.isAssignableFrom(clazz.java)) {
                return false
            }
        }

        return true
    }

    private class Entry(val classes: Array<KClass<*>>, val payload: Any)
}