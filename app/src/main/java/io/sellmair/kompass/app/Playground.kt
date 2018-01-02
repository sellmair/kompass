package io.sellmair.kompass.app

import android.os.Bundle
import io.sellmair.kompass.annotation.Destination
import io.sellmair.kompass.asJuliansDestinatino

/**
 * Created by sebastiansellmair on 09.12.17.
 */


@Destination
class ExampleDestination(val value: Int, var id: Int, val testString: String)

@Destination
data class YoloDestination(val id: Int)


data class Dog(val name: String)

fun test() {
    val bunlde = Bundle()
    bunlde.asJuliansDestinatino()
}


