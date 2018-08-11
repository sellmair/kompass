package io.sellmair.kompass.internal.pipe

import android.support.annotation.UiThread
import io.sellmair.kompass.KompassSail
import io.sellmair.kompass.internal.precondition.Precondition
import io.sellmair.kompass.internal.precondition.requireMainThread


internal class InstructionBuffer<Destination : Any> :
    InstructionPipe<Payload<Destination, Stage.Pending>, Payload<Destination, Stage.Sailed>>,
    Handleable<Payload<Destination, Stage.Sailed>> by Handleable.delegate() {


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
    override operator fun invoke(payload: Payload<Destination, Stage.Pending>) {
        Precondition.requireMainThread()
        onQueue(payload)
    }


    /*
    ################################################################################################
    PRIVATE
    ################################################################################################
    */

    private val queue = mutableListOf<Payload<Destination, Stage.Pending>>()

    @UiThread
    private fun buffer(payload: Payload<Destination, Stage.Pending>) {
        queue.add(payload)
    }

    @UiThread
    private fun executeBuffer(sail: KompassSail) {
        for (payload in queue) {
            handle(sail, payload)
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
    private fun onQueue(payload: Payload<Destination, Stage.Pending>) {
        val sail = this.sail
        when (sail) {
            null -> buffer(payload)
            else -> handle(sail, payload)
        }
    }


    @UiThread
    private fun handle(sail: KompassSail,
                       payload: Payload<Destination, Stage.Pending>) {
        handle(payload.sailed(sail))
    }

}