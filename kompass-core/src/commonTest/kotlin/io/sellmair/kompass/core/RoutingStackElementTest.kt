@file:Suppress("ReplaceAssertBooleanWithAssertEquality")

package io.sellmair.kompass.core

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue

class RoutingStackElementTest {

    private data class RouteImpl(val id: Int) : Route

    private class Impl1<T : Route>(override val route: T, override val key: Key) : RoutingStack.Element<T>()

    private data class Impl2<T : Route>(override val route: T, override val key: Key) : RoutingStack.Element<T>()


    @Test
    fun equals() {
        val i1 = Impl1(RouteImpl(0), Key("key"))
        val i2 = Impl2(RouteImpl(0), Key("key"))
        assertTrue(i1 == i2)
        assertEquals(i1.hashCode(), i2.hashCode())
    }

    @Test
    fun equals_nonEqualKey() {
        val i1 = Impl1(RouteImpl(0), Key())
        val i2 = Impl2(RouteImpl(0), Key())
        assertTrue(i1 != i2)
        assertNotEquals(i1.hashCode(), i2.hashCode())
    }

    @Test
    fun equals_nonEqualRoute() {
        val i1 = Impl1(RouteImpl(0), Key("key"))
        val i2 = Impl2(RouteImpl(1), Key("key"))
        assertTrue(i1 != i2)
        assertNotEquals(i1.hashCode(), i2.hashCode())
    }


}