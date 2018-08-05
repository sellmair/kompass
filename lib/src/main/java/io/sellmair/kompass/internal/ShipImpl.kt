package io.sellmair.kompass.internal

import android.support.annotation.AnyThread
import android.support.annotation.UiThread
import io.sellmair.kompass.*
import io.sellmair.kompass.extension.withKey
import io.sellmair.kompass.internal.pipe.InstructionBuffer
import io.sellmair.kompass.internal.pipe.InstructionCrane
import io.sellmair.kompass.internal.pipe.InstructionRouter
import io.sellmair.kompass.internal.pipe.instruction.Instruction
import io.sellmair.kompass.internal.precondition.Precondition
import io.sellmair.kompass.internal.precondition.requireMainThread
import io.sellmair.kompass.internal.util.mainThread

internal class ShipImpl<Destination : Any>(
    private val name: String,
    private val backStack: BackStack,
    private val map: KompassMap<Destination>,
    private val crane: KompassCrane<Destination>,
    private val detour: KompassDetour<Destination>) :

    KompassShip<Destination>,
    KeyLessBackStack by backStack withKey name {

    private val instructionBuffer = InstructionBuffer<Destination>()
    private val instructionRouter = InstructionRouter(map)
    private var instructionCrane = InstructionCrane(crane)

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
        val instruction = Instruction.StartAt(destination)
        instructionBuffer(instruction)
    }

    @AnyThread
    override fun navigateTo(destination: Destination) = mainThread {
        val instruction = Instruction.NavigateTo(destination)
        instructionBuffer(instruction)
    }

    @AnyThread
    override fun beamTo(destination: Destination) = mainThread {
        val instruction = Instruction.BeamTo(destination)
        instructionBuffer(instruction)
    }


}

