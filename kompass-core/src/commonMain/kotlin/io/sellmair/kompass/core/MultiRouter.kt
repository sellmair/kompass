package io.sellmair.kompass.core

import io.sellmair.kompass.core.internal.Lock


/**
 * # MultiRouter
 * Convenience class that allows for a similar usage than the "Kompass" interface of previous versions of the library.
 * It basically stores (and creates) references to routers for a specified keys
 *
 * ## Note
 * This class is fully thread safe on the JVM.
 *
 * @param factory: Function that creates a router for a specific key.
 * This function will only be called once for each key and the resulting [Router] will be stored
 */
class MultiRouter<K, T : Route>(private val factory: (key: K) -> Router<T>) {

    private val routers = mutableMapOf<K, Router<T>>()

    private val lock = Lock()

    operator fun get(key: K): Router<T> = lock {
        routers.getOrPut(key, { factory(key) })
    }
}