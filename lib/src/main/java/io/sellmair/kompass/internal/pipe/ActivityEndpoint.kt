package io.sellmair.kompass.internal.pipe

import android.content.Intent
import android.os.Bundle
import io.sellmair.kompass.KeyLessBackStack
import io.sellmair.kompass.KompassRoute
import io.sellmair.kompass.KompassSail
import io.sellmair.kompass.internal.pipe.instruction.BundledRoutedSailedInstruction
import io.sellmair.kompass.internal.pipe.instruction.Instruction
import io.sellmair.kompass.internal.pipe.instruction.InstructionEndpoint

internal class ActivityEndpoint<Destination : Any>(
    backStack: KeyLessBackStack) :
    InstructionEndpoint<Destination>,
    Handleable<Unit> by Handleable.delegate(),
    KeyLessBackStack by backStack {
    override fun invoke(instruction: BundledRoutedSailedInstruction<Destination>) {
        val route = instruction.route
        if (route is KompassRoute.Activity<*>) {
            this.route(Endpoint(
                bundle = instruction.bundle,
                route = route,
                sail = instruction.sail,
                instruction = instruction.instruction))
        }
    }

    private inner class Endpoint(
        val bundle: Bundle,
        val route: KompassRoute.Activity<*>,
        val sail: KompassSail,
        val instruction: Instruction<Destination>)


    private fun route(endpoint: Endpoint) {
        val activity = endpoint.sail.activity

        val intent = buildIntent(endpoint)

        activity.startActivity(intent)
        when (endpoint.instruction) {
            is Instruction.StartAt -> activity.finish()
            is Instruction.BeamTo -> activity.finish()
        }
    }

    private fun buildIntent(endpoint: Endpoint): Intent {
        val intent = Intent(
            endpoint.sail.activity,
            endpoint.route.clazz.java)

        intent.putExtras(endpoint.bundle)
        return intent
    }
}