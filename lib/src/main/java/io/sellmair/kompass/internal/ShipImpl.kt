package io.sellmair.kompass.internal

import android.support.annotation.AnyThread
import android.support.annotation.UiThread
import android.support.v4.app.Fragment
import io.sellmair.kompass.*
import io.sellmair.kompass.extension.withKey
import io.sellmair.kompass.internal.pipe.*
import io.sellmair.kompass.internal.precondition.Precondition
import io.sellmair.kompass.internal.precondition.requireMainThread
import io.sellmair.kompass.internal.util.mainThread

internal class ShipImpl<Destination : Any>(
    private val name: String,
    backStack: BackStack,
    map: KompassMap<Destination>,
    crane: KompassCrane<Destination>,
    registry: ExecutableDetourRegistry) :

    KompassShip<Destination>,
    KeyLessBackStack by backStack withKey name,
    InstructionReceiver<Destination> {

    private val fragmentTransitionStack = FragmentTransitionStack.create()

    private val instructionBuffer = InstructionBuffer<Destination>()
    private val instructionRouter = InstructionRouter(map)
    private val instructionCrane = InstructionCrane(crane)
    private val fragmentEndpoint = FragmentEndpoint(fragmentTransitionStack, this, this, registry)
    private val activityEndpoint = ActivityEndpoint<Destination>(this)
    private val viewEndpoint = ViewEndpoint<Destination>(this)

    override fun retainTransitions(fragment: Fragment) {
        fragmentTransitionStack.retain(fragment)
    }

    @UiThread
    override fun setSail(sail: KompassSail): KompassReleasable {
        Precondition.requireMainThread()
        instructionBuffer.sail = sail
        return releasable {
            Precondition.requireMainThread()
            if (instructionBuffer.sail == sail) {
                instructionBuffer.sail = null
            }
        }
    }

    @AnyThread
    override fun startAt(destination: Destination) = mainThread {
        clear()
        val instruction = Instruction.StartAt(destination)
        receive(instruction)
    }

    @AnyThread
    override fun navigateTo(destination: Destination) = mainThread {
        val instruction = Instruction.NavigateTo(destination)
        receive(instruction)
    }

    @AnyThread
    override fun beamTo(destination: Destination) = mainThread {
        val instruction = Instruction.BeamTo(destination)
        receive(instruction)
    }


    override fun receive(instruction: Instruction<Destination>) {
        val payload = Payload(instruction)
        instructionCrane(payload)
    }

    init {
        instructionCrane + instructionBuffer + instructionRouter +
            (fragmentEndpoint / activityEndpoint / viewEndpoint)
    }

}

