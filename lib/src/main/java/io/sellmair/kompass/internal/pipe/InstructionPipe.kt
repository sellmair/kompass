package io.sellmair.kompass.internal.pipe

import android.support.annotation.UiThread

internal interface InstructionPipe<In, Out> : Handleable<Out> {
    @UiThread
    operator fun invoke(instruction: In)
}


