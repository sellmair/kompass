package io.sellmair.kompass.internal.pipe

import android.support.annotation.AnyThread
import android.support.annotation.UiThread
import io.sellmair.kompass.internal.precondition.Precondition
import io.sellmair.kompass.internal.precondition.requireMainThread
import io.sellmair.kompass.internal.util.mainThread

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
    InstructionPipe<In, Out> {

    private var handler: ((Out) -> Unit)? = null

    @UiThread
    override fun invoke(instruction: In) {
        Precondition.requireMainThread()
        first(instruction)
        second(instruction)
    }

    @UiThread
    override fun handle(handler: (out: Out) -> Unit) {
        Precondition.requireMainThread()
        this.handler = handler
    }

    @AnyThread
    override fun handle(value: Out) = mainThread {
        handler?.invoke(value)
    }


    init {
        first.handle(this::handle)
        second.handle(this::handle)
    }
}