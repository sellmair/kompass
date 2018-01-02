package io.sellmair.kompass

/**
 * Created by sebastiansellmair on 02.01.18.
 */
internal class BaseKompassBack(override val key: Any?,
                               override val keySingleton: Boolean,
                               private val action: () -> Boolean) : KompassBack {
    override fun back(): Boolean {
        return action()
    }
}