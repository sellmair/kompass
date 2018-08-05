package io.sellmair.kompass.internal

import io.sellmair.kompass.BackStack
import io.sellmair.kompass.internal.precondition.Precondition
import io.sellmair.kompass.internal.precondition.requireMainThread
import io.sellmair.kompass.internal.util.mainThread
import java.util.*

internal class BackStackImpl : BackStack {

    private val entries = Stack<Entry>()

    private data class Entry(val key: Any?,
                             val keySingle: Boolean,
                             val action: () -> Any)


    override fun back(key: Any?) = mainThread {
        backImmediate(key)
    }


    override fun backImmediate(key: Any?): Boolean {
        Precondition.requireMainThread()

        if (entries.isEmpty()) {
            return false
        }

        if (key == null) {
            entries.pop().action()
            return true
        }

        fun Entry.keyMatches() = this.key == key
        val entry = entries.lastOrNull(Entry::keyMatches)
        if (entry != null) {
            entries.remove(entry)
            entry.action()
            return true
        }

        return false

    }

    override fun onBack(key: Any?, keySingle: Boolean, action: () -> Unit) = mainThread {
        if (key != null && keySingle) {
            remove(key)
        }

        val entry = Entry(key, keySingle, action)
        entries.add(entry)
    }

    override fun remove(key: Any) = mainThread {
        fun Entry.keyMatches() = this.key == key
        entries.removeAll(Entry::keyMatches)
    }


    override fun clear() = mainThread {
        this.entries.clear()
    }


}


