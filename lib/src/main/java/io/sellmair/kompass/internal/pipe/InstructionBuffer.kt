package io.sellmair.kompass.internal.pipe

import android.support.annotation.UiThread
import io.sellmair.kompass.KompassSail
import io.sellmair.kompass.internal.pipe.instruction.Instruction
import io.sellmair.kompass.internal.pipe.instruction.SailedInstruction
import io.sellmair.kompass.internal.precondition.Precondition
import io.sellmair.kompass.internal.precondition.requireMainThread


internal class InstructionBuffer<Destination> :
    InstructionPipe<Instruction<Destination>, SailedInstruction<Destination>>,
    Handleable<SailedInstruction<Destination>> by Handleable.delegate() {


    /*
    ################################################################################################
    INTERNAL API
    ################################################################################################
    */

    @set: UiThread
    @get: UiThread
    internal var sail: KompassSail? = null
        set(value) {
            Precondition.requireMainThread()
            onSetSail(value)
            field = value
        }

    @UiThread
    override operator fun invoke(instruction: Instruction<Destination>) {
        Precondition.requireMainThread()
        onQueue(instruction)
    }


    /*
    ################################################################################################
    PRIVATE
    ################################################################################################
    */

    private val queue = mutableListOf<Instruction<Destination>>()

    @UiThread
    private fun buffer(instruction: Instruction<Destination>) {
        queue.add(instruction)
    }

    @UiThread
    private fun executeBuffer(sail: KompassSail) {
        for (instruction in queue) {
            handle(sail, instruction)
        }

        queue.clear()
    }

    @UiThread
    private fun onSetSail(sail: KompassSail?) {
        if (sail != null) {
            executeBuffer(sail)
        }
    }

    @UiThread
    private fun onQueue(instruction: Instruction<Destination>) {
        val sail = this.sail
        when (sail) {
            null -> buffer(instruction)
            else -> handle(sail, instruction)
        }
    }


    @UiThread
    private fun handle(sail: KompassSail,
                       instruction: Instruction<Destination>) {
        handle(SailedInstruction(sail, instruction))
    }

}