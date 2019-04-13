package io.sellmair.kompass.android.test.fragment

import kotlinx.android.parcel.Parcelize
import kotlin.random.Random

@Parcelize
data class RouteOne(override val text: String = "RouteOne_${randomString()}") : BaseRoute

@Parcelize
data class RouteTwo(override val text: String = "RouteTwo_${randomString()}") : BaseRoute

@Parcelize
data class RouteThree(override val text: String = "RouteThree_${randomString()}") : BaseRoute

@Parcelize
data class RouteFour(override val text: String = "RouteFour_${randomString()}") : BaseRoute

@Parcelize
data class RouteFive(override val text: String = "RouteFive_${randomString()}") : BaseRoute

@Parcelize
data class RouteSix(override val text: String = "RouteSix_${randomString()}") : BaseRoute

@Parcelize
data class RouteSeven(override val text: String = "RouteSeven_${randomString()}") : BaseRoute

@Parcelize
data class RouteEight(override val text: String = "RouteEight_${randomString()}") : BaseRoute

@Parcelize
data class RouteNine(override val text: String = "RouteNine_${randomString()}") : BaseRoute


private fun randomString() = Random.nextBytes(16)
    .map { byte -> byte.toInt() and 0xFF }
    .joinToString("") { it.toString(16) }
