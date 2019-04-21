package io.sellmair.kompass.android

import android.os.Parcelable
import io.sellmair.kompass.core.Route
import io.sellmair.kompass.core.RoutingStack
import kotlinx.android.parcel.Parcelize


fun <T> RoutingStack<T>.parcelable(): ParcelableRoutingStack<T> where T : Route, T : Parcelable {
    return when (this) {
        is ParcelableRoutingStack<T> -> this
        else -> ParcelableRoutingStackWrapper(this.elements.map { element -> element.parcelable() })
    }
}

fun <T> RoutingStack.Element<T>.parcelable(): ParcelableElement<T> where T : Route, T : Parcelable {
    return when (this) {
        is ParcelableElement -> this
        else -> ParcelableElement(key.parcelable(), route)
    }
}

fun <T> Iterable<RoutingStack.Element<T>>.parcelable() where T : Route, T : Parcelable =
    this.map { entry -> entry.parcelable() }

@Parcelize
private class ParcelableRoutingStackWrapper<T>(override val elements: List<ParcelableElement<T>>) :
    ParcelableRoutingStack<T> where T : Route, T : Parcelable {

    override fun with(elements: Iterable<RoutingStack.Element<T>>): ParcelableRoutingStack<T> {
        return ParcelableRoutingStackWrapper(elements.map { element -> element.parcelable() })
    }
}

@Parcelize
data class ParcelableElement<T>(
    override val key: ParcelableKey,
    override val route: T
) : RoutingStack.Element<T>(), Parcelable where T : Route, T : Parcelable


