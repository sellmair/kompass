package io.sellmair.kompass.core

import io.sellmair.kompass.core.internal.Lock

class MultiRouter<K, T : Route>(private val factory: (key: K) -> Router<T>) {

    private val routers = mutableMapOf<K, Router<T>>()

    private val lock = Lock()

    operator fun get(key: K): Router<T> = lock {
        routers.getOrPut(key, { factory(key) })
    }
}