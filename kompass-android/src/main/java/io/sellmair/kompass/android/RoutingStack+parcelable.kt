package io.sellmair.kompass.android

import android.os.Parcelable
import io.sellmair.kompass.core.Route
import io.sellmair.kompass.core.RoutingStack
import kotlinx.android.parcel.Parcelize

/**
 * @return [ParcelableRoutingStack] wrapper for the current stack, or this instance if it already
 * implements [ParcelableRoutingStack]
 */
fun <T> RoutingStack<T>.parcelable(): ParcelableRoutingStack<T> where T : Route, T : Parcelable {
    return when (this) {
        is ParcelableRoutingStack<T> -> this
        else -> ParcelableRoutingStackWrapper(this.elements.map { element -> element.parcelable() })
    }
}


@Parcelize
private class ParcelableRoutingStackWrapper<T>(override val elements: List<ParcelableElement<T>>) :
    ParcelableRoutingStack<T> where T : Route, T : Parcelable {

    override fun with(elements: Iterable<RoutingStack.Element<T>>): ParcelableRoutingStack<T> {
        return ParcelableRoutingStackWrapper(elements.map { element -> element.parcelable() })
    }
}


/**
 * @return [ParcelableElement] wrapper for the current element of this instance if it already implemennts
 * [ParcelableElement]
 */
fun <T> RoutingStack.Element<T>.parcelable(): ParcelableElement<T> where T : Route, T : Parcelable {
    return when (this) {
        is ParcelableElement -> this
        else -> ParcelableElement(key.parcelable(), route)
    }
}
