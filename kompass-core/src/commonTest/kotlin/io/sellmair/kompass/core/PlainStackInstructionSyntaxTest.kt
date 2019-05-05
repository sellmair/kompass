package io.sellmair.kompass.core

import io.sellmair.kompass.core.internal.ElementImpl
import io.sellmair.kompass.core.internal.RoutingStackImpl
import kotlin.test.Test
import kotlin.test.assertEquals


class PlainStackInstructionSyntaxTest {

    @Test
    fun push() {
        val stack = RoutingStackImpl(emptyList())
        val newStack = stack.push(RouteImpl(0))
        assertEquals(1, newStack.elements.size)
        assertEquals(RouteImpl(0), newStack.elements.first().route)
    }

    @Test
    fun pushDistinct() {
        val stack = RoutingStack.from(RouteImpl(0), RouteImpl(1), RouteImpl(2))
        val newStack = stack.pushDistinct(RouteImpl(1))
        assertEquals(3, newStack.elements.size)
        assertEquals(0, newStack.elements[0].route.id)
        assertEquals(2, newStack.elements[1].route.id)
        assertEquals(1, newStack.elements[2].route.id)
    }

    @Test
    fun pop() {
        val stack = RoutingStack.from(RouteImpl(0), RouteImpl(1), RouteImpl(2))
        val newStack = stack.pop()
        assertEquals(3, stack.elements.size)
        assertEquals(2, newStack.elements.size)
        assertEquals(RouteImpl(0), newStack.elements[0].route)
        assertEquals(RouteImpl(1), newStack.elements[1].route)
    }

    @Test
    fun pop_whenEmpty() {
        val stack = RoutingStack.from(RouteImpl(0), RouteImpl(1), RouteImpl(2))
        val newStack = stack.pop().pop().pop().pop()
        assertEquals(3, stack.elements.size)
        assertEquals(0, newStack.elements.size)
    }

    @Test
    fun clear() {
        val stack = RoutingStack.from(RouteImpl(0), RouteImpl(1))
        val newStack = stack.clear()
        assertEquals(2, stack.elements.size)
        assertEquals(0, newStack.elements.size)
    }

    @Test
    fun popUntil() {
        val stack = RoutingStack.from(RouteImpl(0), RouteImpl(1), RouteImpl(2), RouteImpl(1), RouteImpl(2))
        val newStack = stack.popUntil { route -> route == RouteImpl(1) }
        assertEquals(5, stack.elements.size)
        assertEquals(4, newStack.elements.size)
        assertEquals(RouteImpl(0), newStack.elements[0].route)
        assertEquals(RouteImpl(1), newStack.elements[1].route)
        assertEquals(RouteImpl(2), newStack.elements[2].route)
        assertEquals(RouteImpl(1), newStack.elements[3].route)
    }

    @Test
    fun popUntilRoute() {
        val stack = RoutingStack.from(RouteImpl(0), RouteImpl(1), RouteImpl(2), RouteImpl(1), RouteImpl(2))
        val newStack = stack.popUntilRoute(RouteImpl(1))
        assertEquals(5, stack.elements.size)
        assertEquals(4, newStack.elements.size)
        assertEquals(RouteImpl(0), newStack.elements[0].route)
        assertEquals(RouteImpl(1), newStack.elements[1].route)
        assertEquals(RouteImpl(2), newStack.elements[2].route)
        assertEquals(RouteImpl(1), newStack.elements[3].route)
    }

    @Test
    fun push_element() {
        val stack = RoutingStack.from(RouteImpl(0), RouteImpl(1))
        val newStack = stack.push(ElementImpl(RouteImpl(2)))
        assertEquals(2, stack.elements.size)
        assertEquals(3, newStack.elements.size)
        assertEquals(RouteImpl(2), newStack.elements[2].route)
    }

    @Test
    fun push_element_whichIsAlreadyInStack() {
        val stack = RoutingStack.from(RouteImpl(0), RouteImpl(1))
        val newStack = stack.push(stack.elements.first())
        assertEquals(2, stack.elements.size)
        assertEquals(2, newStack.elements.size)
        assertEquals(RouteImpl(1), newStack.elements[0].route)
        assertEquals(RouteImpl(0), newStack.elements[1].route)
    }


    @Test
    fun replaceTopWith_onEmptyStack() {
        val stack = RoutingStack.empty<Route>()
        val newStack = stack.replaceTopWith(RouteImpl(0))
        assertEquals(1, newStack.elements.size)
        assertEquals(RouteImpl(0), newStack.elements.first().route)
    }


    @Test
    fun replaceTopWith_onNonEmptyStack() {
        val stack = RoutingStack.from(RouteImpl(0), RouteImpl(1), RouteImpl(2))
        val newStack = stack.replaceTopWith(RouteImpl(3))
        assertEquals(3, stack.elements.size)
        assertEquals(RouteImpl(0), newStack.elements[0].route)
        assertEquals(RouteImpl(1), newStack.elements[1].route)
        assertEquals(RouteImpl(3), newStack.elements[2].route)
    }


    @Test
    fun replaceTopWith_onNonEmptyStack_withDuplicateKey() {
        val stack = RoutingStack.from(RouteImpl(0), RouteImpl(1), RouteImpl(2))
        val newStack = stack.replaceTopWith(RoutingStack.Element(RouteImpl(3), key = stack.first().key))
        assertEquals(2, newStack.elements.size)
        assertEquals(RouteImpl(1), newStack.elements[0].route)
        assertEquals(RouteImpl(3), newStack.elements[1].route)
    }


}


private data class RouteImpl(val id: Int) : Route