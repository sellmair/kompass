package io.sellmair.kompass.core.internal

internal expect class Lock constructor() {
    operator fun <T> invoke(action: () -> T): T
}