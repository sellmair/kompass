package io.sellmair.kompass.core

import io.sellmair.kompass.core.internal.ElementImpl
import io.sellmair.kompass.core.internal.RoutingStackImpl
import kotlin.jvm.JvmName

interface RoutingStack<T : Route> : RoutingStackInstructionSyntax<T, RoutingStack<T>>,
    Iterable<RoutingStack.Element<T>> {

    val elements: List<Element<T>>

    fun with(elements: Iterable<Element<T>> = this.elements): RoutingStack<T>

    override fun iterator(): Iterator<Element<T>> {
        return elements.iterator()
    }

    override fun stackInstruction(instruction: RoutingStackInstruction<T>): RoutingStack<T> {
        return with(elements.instruction())
    }

    abstract class Element<T : Route> {

        abstract val key: Key

        abstract val route: T

        override fun toString(): String {
            return "Element(key=${key.value}, route=$route)"
        }

        final override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other !is Element<*>) return false
            if (this.key != other.key) return false
            if (this.route != other.route) return false
            return true
        }

        final override fun hashCode(): Int {
            var hashCode = 31
            hashCode += 31 * this.key.hashCode()
            hashCode += 31 * this.route.hashCode()
            return hashCode
        }

        companion object Factory {
            operator fun <T : Route> invoke(route: T, key: Key = Key()): Element<T> =
                ElementImpl(route, key)
        }

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