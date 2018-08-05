package io.sellmair.kompass.extension

import io.sellmair.kompass.BackStack
import io.sellmair.kompass.KeyLessBackStack
import io.sellmair.kompass.internal.KeyLessBackStackAdapter

infix fun BackStack.withKey(key: Any): KeyLessBackStack {
    return KeyLessBackStackAdapter(key, this)
}