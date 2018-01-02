package io.sellmair.kompass.app

import io.sellmair.kompass.annotation.Destination
import io.sellmair.kompass.app.model.SimpleParcelable

/**
 * Created by sebastiansellmair on 10.12.17.
 */
@Destination
data class SimpleDestination(val id: Int, val justALong: Long, val name: String)

@Destination
class SimpleParcelDestination(val id: Int, val parcel: SimpleParcelable)

@Destination
class SimpleIntListDestination(val name: String, val ids: List<Int>)

class SimpleIntArrayDestination(val name: String, val ids: IntArray)

@Destination
class MaltesMostWantedDestination(val name: String, val ids: List<Int>, val myParcel: SimpleParcelable)


@Destination
class JuliansDestinatino(val name: String, val ids: List<Int>)