package io.sellmair.kompass.bundle

/**
 * Created by sebastiansellmair on 09.12.17.
 */
interface DestinationSerializer {
    @Throws(MissingSerializerException::class)
    fun writeDestination(destination: Any, jvmBundle: JVMBundle)
}