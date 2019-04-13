package io.sellmair.kompass.core

import io.sellmair.kompass.core.internal.ElementImpl
import io.sellmair.kompass.core.internal.RoutingStackImpl
import kotlin.jvm.JvmName

interface RoutingStack<T : Route> {

    val elements: List<Element<T>>

    fun with(elements: Iterable<Element<T>> = this.elements): RoutingStack<T>


    interface Element<T : Route> {
        val key: Key
        val route: T
    }

    companion object Factory {
        fun <T : Route> empty(): RoutingStack<T> = RoutingStackImpl(emptyList())

        fun <T : Route> just(element: T): RoutingStack<T> = RoutingStackImpl(listOf(element).toEntries())

        @JvmName("from")
        fun <T : Route> from(vararg elements: T): RoutingStack<T> = RoutingStackImpl(elements.toList().toEntries())

        @JvmName("fromIterable")
        fun <T : Route> from(elements: Iterable<T>): RoutingStack<T> = RoutingStackImpl(elements.toList().toEntries())

        @JvmName("fromArray")
        fun <T : Route> from(elements: Array<T>): RoutingStack<T> = RoutingStackImpl(elements.toList().toEntries())

        @JvmName("fromSequence")
        fun <T : Route> from(elements: Sequence<T>): RoutingStack<T> = RoutingStackImpl(elements.toList().toEntries())
    }
}


private fun <T : Route> Iterable<T>.toEntries() =
    this.map { element -> ElementImpl(element) }.map { it as RoutingStack.Element<T> }