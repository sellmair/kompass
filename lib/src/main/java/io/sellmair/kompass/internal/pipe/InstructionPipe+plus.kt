package io.sellmair.kompass.internal.pipe

import android.support.annotation.UiThread
import io.sellmair.kompass.internal.precondition.Precondition
import io.sellmair.kompass.internal.precondition.requireMainThread


/*
################################################################################################
INTERNAL API
################################################################################################
*/

internal operator fun <In, Intermediate, Out> InstructionPipe<In, Intermediate>.plus(
    other: InstructionPipe<Intermediate, Out>): InstructionPipe<In, Out> {
    return InstructionPipePlusConnector(this, other)
}


/*
################################################################################################
PRIVATE IMPLEMENTATION
################################################################################################
*/

private class InstructionPipePlusConnector<In, Intermediate, Out>(
    private val first: InstructionPipe<In, Intermediate>,
    second: InstructionPipe<Intermediate, Out>) :
    InstructionPipe<In, Out>,
    Handleable<Out> by Handleable.delegate() {


    @UiThread
    override fun invoke(payload: In) {
        Precondition.requireMainThread()
        first(payload)
    }

    init {
        first.handle(second::invoke)
        second.handle(this::handle)
    }
}