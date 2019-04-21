package io.sellmair.kompass.core

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotSame
import kotlin.test.assertSame

class MultiRouterTest {

    @Test
    fun get_returnsInstance() {
        val multiRouter = MultiRouter<Int, Route> { key -> TestRouter(key) }
        assertEquals(0, (multiRouter[0] as TestRouter).id)
    }

    @Test
    fun get_returnsSameInstance() {
        val multiRouter = MultiRouter<Int, Route> { key -> TestRouter(key) }
        assertEquals(0, (multiRouter[0] as TestRouter).id)
        assertSame(multiRouter[0], multiRouter[0])
    }

    @Test
    fun get_returnsDifferentInstance_forDifferentKey() {
        val multiRouter = MultiRouter<Int, Route> { key -> TestRouter(key) }
        assertEquals(0, (multiRouter[0] as TestRouter).id)
        assertNotSame(multiRouter[0], multiRouter[1])
    }
}

private class TestRouter(val id: Int) : Router<Route> {
    override fun instruction(instruction: RouterInstruction<Route>) = Unit
}