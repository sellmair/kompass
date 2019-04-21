package io.sellmair.kompass.core

import io.sellmair.kompass.core.RoutingStack.Element
import io.sellmair.kompass.core.internal.ElementImpl
import io.sellmair.kompass.core.internal.RoutingStackImpl
import kotlin.jvm.JvmName

/**
 * # RoutingStack
 * Represents a "routing state" where the most top (last) [Element] is representing the currently "active" (displayed)
 * route and the most bottom (first) [Element] the "root". The two most common operations of a [RoutingStack]
 * are "push" and "pop"
 * - push: Adds a route to the top of the stack.
 * e.g. navigating from the "HomeRoute" to the "SettingsRoute"
 * - pop: Removes the currently "active" route from the top of this stack
 * e.g. pressing a back button in the "SettingsRoute" will lead to the "HomeRoute" getting active again
 *
 * ## Note
 * - Implementations of [RoutingStack] should implement a [equals] and [hashCode] function that makes
 * [RoutingStack]'s comparable
 *
 * - Implementations of [RoutingStack] should always be implemented *immutable*
 */
interface RoutingStack<T : Route> : PlainStackInstructionSyntax<T, RoutingStack<T>>, Iterable<Element<T>> {

    val elements: List<Element<T>>

    fun with(elements: Iterable<Element<T>> = this.elements): RoutingStack<T>

    override fun iterator(): Iterator<Element<T>> {
        return elements.iterator()
    }

    override fun plainStackInstruction(instruction: PlainStackInstruction<T>): RoutingStack<T> {
        return with(elements.instruction())
    }

    abstract class Element<out T : Route> {

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

    @Suppress("unused")
    companion object Factory {
        fun <T : Route> empty(): RoutingStack<T> = RoutingStackImpl(emptyList())

        fun <T : Route> just(element: T): RoutingStack<T> = RoutingStackImpl(listOf(element).toElements())

        @JvmName("from")
        fun <T : Route> from(vararg elements: T): RoutingStack<T> = RoutingStackImpl(elements.toList().toElements())

        @JvmName("fromIterable")
        fun <T : Route> from(elements: Iterable<T>): RoutingStack<T> = RoutingStackImpl(elements.toList().toElements())

        @JvmName("fromArray")
        fun <T : Route> from(elements: Array<T>): RoutingStack<T> = RoutingStackImpl(elements.toList().toElements())

        @JvmName("fromSequence")
        fun <T : Route> from(elements: Sequence<T>): RoutingStack<T> = RoutingStackImpl(elements.toList().toElements())
    }
}


private fun <T : Route> Iterable<T>.toElements() =
    this.map { element -> ElementImpl(element) }