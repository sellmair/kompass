package io.sellmair.kompass.app

import io.sellmair.kompass.annotation.Destination

/**
 * Created by sebastiansellmair on 09.12.17.
 */


@Destination
class ExampleDestination(val value: Int, var id: Int, val testString: String)

@Destination
data class YoloDestination(val id: Int)


data class Dog(val name: String)

fun test() {

}


