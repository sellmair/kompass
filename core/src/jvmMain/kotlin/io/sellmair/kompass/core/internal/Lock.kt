package io.sellmair.kompass.core.internal

import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock

actual class Lock {

    private val reentrantLock = ReentrantLock()

    actual operator fun <T> invoke(action: () -> T): T = reentrantLock.withLock(action)

}