package io.sellmair.kompass.compiler

import io.sellmair.kompass.bundle.DestinationSerializer
import io.sellmair.kompass.bundle.JVMBundle

/**
 * Created by sebastiansellmair on 09.12.17.
 */
class KompassSerializer(serializer: List<DestinationProcessor.Serializer>) : DestinationSerializer {


    override fun writeDestination(destination: Any, jvmBundle: JVMBundle) {

    }

}