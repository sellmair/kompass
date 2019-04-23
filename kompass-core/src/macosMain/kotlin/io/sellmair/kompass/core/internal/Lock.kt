package io.sellmair.kompass.core.internal

internal actual class Lock {
    actual operator fun <T> invoke(action: () -> T): T {
        return action()
    }
}