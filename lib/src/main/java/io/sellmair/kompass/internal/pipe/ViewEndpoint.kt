package io.sellmair.kompass.internal.pipe

import android.view.ViewGroup

private typealias VEndpoint<Destination> = Payload<Destination, Stage.Endpoint.View>

internal class ViewEndpoint<Destination : Any> :
    InstructionEndpoint<Destination>,
    Handleable<Unit> by Handleable.delegate() {

    override fun invoke(payload: Payload<Destination, Stage.Routed>) {
        val endpoint = payload.viewEndpoint()
        if (endpoint != null) {

        }
    }

    private fun route(endpoint: VEndpoint<Destination>) {
        applyViewBundle(endpoint)
        when (endpoint.instruction) {
            is Instruction.StartAt -> startAt(endpoint)
            is Instruction.NavigateTo -> navigateTo(endpoint)
            is Instruction.BeamTo -> beamTo(endpoint)
        }
    }


    private fun startAt(endpoint: VEndpoint<Destination>) {
        val sail = endpoint.sail
        val container = sail.activity.findViewById<ViewGroup>(sail.containerId)
        container.removeAllViews()
        container.addView(endpoint.route.view)
    }


    private fun navigateTo(endpoint: VEndpoint<Destination>) {

    }

    private fun beamTo(endpoint: VEndpoint<Destination>) {

    }

    private fun applyViewBundle(endpoint: VEndpoint<Destination>) {
        val view = endpoint.route.view
        val bundle = endpoint.bundle

    }

}
