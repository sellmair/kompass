package io.sellmair.kompass.internal

import io.sellmair.kompass.BackStack
import io.sellmair.kompass.KeyLessBackStack

internal class KeyLessBackStackAdapter(
    private val key: Any,
    private val backStack: BackStack) : KeyLessBackStack {

    override fun back() {
        backStack.back(key)
    }

    override fun backImmediate(): Boolean {
        return backStack.backImmediate(key)
    }

    override fun onBack(action: () -> Unit) {
        backStack.onBack(
            key = key,
            keySingle = false,
            action = action)
    }

    override fun clear() {
        backStack.remove(key)
    }
}

