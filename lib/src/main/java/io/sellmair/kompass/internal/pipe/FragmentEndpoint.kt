package io.sellmair.kompass.internal.pipe

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import io.sellmair.kompass.KeyLessBackStack
import io.sellmair.kompass.KompassSail
import io.sellmair.kompass.internal.ExecutableDetourRegistry


private typealias FEndpoint<Destination> = Payload<Destination, Stage.Endpoint.Fragment>


internal class FragmentEndpoint<Destination : Any>(
    backStack: KeyLessBackStack,
    detourRegistry: ExecutableDetourRegistry) :
    InstructionEndpoint<Destination>,
    Handleable<Unit> by Handleable.delegate(),
    KeyLessBackStack by backStack,
    ExecutableDetourRegistry by detourRegistry {


    override fun invoke(payload: Payload<Destination, Stage.Routed>) {
        val endpoint = payload.fragmentEndpoint()
        if (endpoint != null) {
            route(endpoint)
        }
    }


    private fun route(endpoint: Payload<Destination, Stage.Endpoint.Fragment>) {
        applyFragmentArguments(endpoint)

        when (endpoint.instruction) {
            is Instruction.StartAt -> startAt(endpoint)
            is Instruction.NavigateTo -> navigateTo(endpoint)
            is Instruction.BeamTo -> beamTo(endpoint)
        }
    }

    private fun startAt(endpoint: FEndpoint<Destination>) {
        (this as KeyLessBackStack).clear()

        val manager = endpoint.sail.manager
        val containerId = endpoint.sail.containerId
        val fragment = endpoint.route.fragment

        val transaction = manager.beginTransaction()

        applyDetour(endpoint, transaction)

        transaction.replace(containerId, fragment)
        transaction.commit()

    }

    private fun navigateTo(endpoint: FEndpoint<Destination>) {
        val manager = endpoint.sail.manager
        val containerId = endpoint.sail.containerId
        val fragment = endpoint.route.fragment

        val transaction = manager.beginTransaction()
        applyDetour(endpoint, transaction)

        transaction.replace(containerId, fragment)
        transaction.addToBackStack(null)
        transaction.commit()

        onBack { manager.popBackStackImmediate() }

    }

    private fun beamTo(endpoint: FEndpoint<Destination>) {
        val manager = endpoint.sail.manager
        val containerId = endpoint.sail.containerId
        val fragment = endpoint.route.fragment


        val transaction = manager.beginTransaction()
        applyDetour(endpoint, transaction)

        transaction.replace(containerId, fragment)
        transaction.addToBackStack(null)
        transaction.commit()

        onBack {
            manager.popBackStackImmediate()
            backImmediate()
        }
    }


    private fun applyFragmentArguments(endpoint: FEndpoint<Destination>) {
        val fragment = endpoint.route.fragment
        fragment.arguments = endpoint.bundle
    }


    private fun applyDetour(endpoint: FEndpoint<Destination>, transaction: FragmentTransaction) {
        val destination = endpoint.instruction.destination
        val currentFragment = findCurrentFragment(endpoint.sail)
        val nextFragment = endpoint.route.fragment
        setupFragmentDetour(
            destination = destination,
            current = currentFragment,
            next = nextFragment,
            transaction = transaction)
    }

    private fun findCurrentFragment(sail: KompassSail): Fragment {
        val manager = sail.manager
        val id = sail.containerId
        return manager.findFragmentById(id) ?: Fragment()
    }
}
