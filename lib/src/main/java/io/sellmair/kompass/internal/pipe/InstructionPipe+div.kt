package io.sellmair.kompass.internal.pipe

import android.support.annotation.UiThread
import io.sellmair.kompass.internal.precondition.Precondition
import io.sellmair.kompass.internal.precondition.requireMainThread

internal operator fun <In, Out>
    InstructionPipe<In, Out>.div(other: InstructionPipe<In, Out>): InstructionPipe<In, Out> {
    return InstructionPipeDivConnector(this, other)
}


/*
################################################################################################
PRIVATE IMPLEMENTATION
################################################################################################
*/

private class InstructionPipeDivConnector<In, Out>(
    private val first: InstructionPipe<In, Out>,
    private val second: InstructionPipe<In, Out>) :
    InstructionPipe<In, Out>,
    Handleable<Out> by Handleable.delegate() {


    @UiThread
    override fun invoke(payload: In) {
        Precondition.requireMainThread()
        first(payload)
        second(payload)
    }


    init {
        first.handle(this::handle)
        second.handle(this::handle)
    }
}