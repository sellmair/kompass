package io.sellmair.kompass.core.internal

expect class Lock constructor() {
    operator fun <T> invoke(action: () -> T): T
}