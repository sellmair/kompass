package io.sellmair.kompass.internal.pipe

import android.app.Activity
import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.view.View
import io.sellmair.kompass.KompassRoute
import io.sellmair.kompass.KompassSail
import io.sellmair.kompass.invoke
import io.sellmair.kompass.stub
import org.junit.Assert
import org.junit.Test

class PayloadTest {

    @Test
    fun instantiate() {
        val payload: Payload<PseudoDestination, Unit> = Payload()

        for (member in Payload.Member.values()) {
            Assert.assertNull(payload[member])
        }
    }

    @Test
    fun pending() {
        val payload: Payload<PseudoDestination, Unit> = Payload()
        val pending = payload.pending(pseudoInstruction)

        for (member in Payload.Member.values()) {
            val value = pending[member]
            when (member) {
                Payload.Member.INSTRUCTION -> Assert.assertEquals(pseudoInstruction, value)
                Payload.Member.BUNDLE -> Assert.assertNull(value)
                Payload.Member.SAIL -> Assert.assertNull(value)
                Payload.Member.ROUTE -> Assert.assertNull(value)
            }
        }

        Assert.assertEquals(pseudoInstruction, pending.instruction)
    }


    @Test
    fun craned() {
        val payload = Payload(pseudoInstruction)
        val crained = payload.craned(pseudoBundle)

        for (member in Payload.Member.values()) {
            val value = crained[member]
            when (member) {
                Payload.Member.INSTRUCTION -> Assert.assertEquals(pseudoInstruction, value)
                Payload.Member.BUNDLE -> Assert.assertEquals(pseudoBundle, value)
                Payload.Member.SAIL -> Assert.assertNull(value)
                Payload.Member.ROUTE -> Assert.assertNull(value)
            }
        }

        Assert.assertEquals(pseudoInstruction, crained.instruction)
        Assert.assertEquals(pseudoBundle, crained.bundle)
    }


    @Test
    fun sailed() {
        val payload = Payload(pseudoInstruction)
        val craned = payload.craned(pseudoBundle)
        val sailed = craned.sailed(pseudoSail)


        for (member in Payload.Member.values()) {
            val value = sailed[member]
            when (member) {
                Payload.Member.INSTRUCTION -> Assert.assertEquals(pseudoInstruction, value)
                Payload.Member.BUNDLE -> Assert.assertEquals(pseudoBundle, value)
                Payload.Member.SAIL -> Assert.assertEquals(pseudoSail, value)
                Payload.Member.ROUTE -> Assert.assertNull(value)
            }
        }

        Assert.assertEquals(pseudoInstruction, sailed.instruction)
        Assert.assertEquals(pseudoBundle, sailed.bundle)
        Assert.assertEquals(pseudoSail, sailed.sail)
    }

    @Test
    fun routed() {
        val payload = Payload(pseudoInstruction)
        val craned = payload.craned(pseudoBundle)
        val sailed = craned.sailed(pseudoSail)
        val routed = sailed.routed(pseudoRoute)


        for (member in Payload.Member.values()) {
            val value = routed[member]
            when (member) {
                Payload.Member.INSTRUCTION -> Assert.assertEquals(pseudoInstruction, value)
                Payload.Member.BUNDLE -> Assert.assertEquals(pseudoBundle, value)
                Payload.Member.SAIL -> Assert.assertEquals(pseudoSail, value)
                Payload.Member.ROUTE -> Assert.assertEquals(pseudoRoute, value)
            }
        }

        Assert.assertEquals(pseudoInstruction, routed.instruction)
        Assert.assertEquals(pseudoBundle, routed.bundle)
        Assert.assertEquals(pseudoSail, routed.sail)
        Assert.assertEquals(pseudoRoute, routed.route)
    }


    /*
    ################################################################################################
    MOCKS / PSEUDOS
    ################################################################################################
    */

    private interface PseudoDestination

    private val pseudoDestination = object : PseudoDestination {}

    private val pseudoInstruction: Instruction<PseudoDestination> =
        Instruction.StartAt(pseudoDestination)

    private val pseudoSail = object : KompassSail {
        override val activity: Activity get() = stub()
        override val manager: FragmentManager get() = stub()
        override val containerId: Int get() = stub()
    }

    private val pseudoBundle = Bundle()

    private val pseudoRoute: KompassRoute = KompassRoute(View::class)

}