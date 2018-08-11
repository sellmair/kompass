package io.sellmair.kompass.internal.pipe

import android.content.Intent
import io.sellmair.kompass.KeyLessBackStack

private typealias AEndpoint<Destination> = Payload<Destination, Stage.Endpoint.Activity>

internal class ActivityEndpoint<Destination : Any>(
    backStack: KeyLessBackStack) :
    InstructionEndpoint<Destination>,
    Handleable<Unit> by Handleable.delegate(),
    KeyLessBackStack by backStack {
    override fun invoke(payload: Payload<Destination, Stage.Routed>) {
        val endpoint = payload.activityEndpoint()
        if (endpoint != null) {
            route(endpoint)
        }
    }


    private fun route(endpoint: AEndpoint<Destination>) {
        val activity = endpoint.sail.activity

        val intent = buildIntent(endpoint)

        activity.startActivity(intent)
        when (endpoint.instruction) {
            is Instruction.StartAt -> activity.finish()
            is Instruction.BeamTo -> activity.finish()
        }
    }

    private fun buildIntent(endpoint: AEndpoint<Destination>): Intent {
        val intent = Intent(
            endpoint.sail.activity,
            endpoint.route.clazz.java)

        intent.putExtras(endpoint.bundle)
        return intent
    }
}