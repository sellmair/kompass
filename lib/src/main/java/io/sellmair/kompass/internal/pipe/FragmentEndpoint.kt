package io.sellmair.kompass.internal.pipe

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import io.sellmair.kompass.KeyLessBackStack
import io.sellmair.kompass.KompassSail
import io.sellmair.kompass.extension.resetTransitions
import io.sellmair.kompass.internal.ExecutableDetourRegistry
import io.sellmair.kompass.internal.FragmentTransitionStack
import io.sellmair.kompass.internal.InstructionReceiver


private typealias FEndpoint<Destination> = Payload<Destination, Stage.Endpoint.Fragment>


internal class FragmentEndpoint<Destination : Any>(
    private val fragmentTransitionStack: FragmentTransitionStack,
    instructionReceiver: InstructionReceiver<Destination>,
    backStack: KeyLessBackStack,
    detourRegistry: ExecutableDetourRegistry) :
    InstructionEndpoint<Destination>,
    InstructionReceiver<Destination> by instructionReceiver,
    Handleable<Unit> by Handleable.delegate(),
    KeyLessBackStack by backStack,
    ExecutableDetourRegistry by detourRegistry {


    override fun invoke(payload: Payload<Destination, Stage.Routed>) {
        val endpoint = payload.fragmentEndpoint()
        if (endpoint != null) {
            route(endpoint)
        }
    }


    private fun route(endpoint: FEndpoint<Destination>) {
        applyFragmentArguments(endpoint)

        when (endpoint.instruction) {
            is Instruction.StartAt -> startAt(endpoint)
            is Instruction.NavigateTo -> navigateTo(endpoint)
            is Instruction.BeamTo -> beamTo(endpoint)
            is PopBackStackImmediateInstruction -> popBackStackImmediate(endpoint)
        }
    }

    /*
    ################################################################################################
    PRIVATE IMPLEMENTATION: Handle different instructions
    ################################################################################################
    */

    private fun startAt(endpoint: FEndpoint<Destination>) {
        (this as KeyLessBackStack).clear()

        val manager = endpoint.sail.manager
        val containerId = endpoint.sail.containerId
        val fragment = endpoint.route.fragment

        val transaction = manager.beginTransaction()

        applyForwardDetour(endpoint, transaction)

        transaction.replace(containerId, fragment)
        transaction.commit()

    }

    private fun navigateTo(endpoint: FEndpoint<Destination>) {
        val manager = endpoint.sail.manager
        val containerId = endpoint.sail.containerId
        val fragment = endpoint.route.fragment

        val transaction = manager.beginTransaction()
        applyForwardDetour(endpoint, transaction)

        transaction.replace(containerId, fragment)
        transaction.addToBackStack(null)
        transaction.commit()

        onBack { receive(PopBackStackImmediateInstruction(endpoint.instruction.destination)) }
    }

    private fun beamTo(endpoint: FEndpoint<Destination>) {
        val manager = endpoint.sail.manager
        val containerId = endpoint.sail.containerId
        val fragment = endpoint.route.fragment

        val transaction = manager.beginTransaction()
        applyForwardDetour(endpoint, transaction)

        manager.popBackStackImmediate()

        transaction.replace(containerId, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }


    private fun popBackStackImmediate(endpoint: FEndpoint<Destination>) {
        applyBackwardDetour(endpoint)
        endpoint.sail.manager.popBackStackImmediate()
    }


    /*
    ################################################################################################
    PRIVATE IMPLEMENTATION: FragmentManager.popBackStackImmediate instructions
    ################################################################################################
    */

    class PopBackStackImmediateInstruction<Destination : Any>(
        destination: Destination) : Instruction.Generic<Destination>(destination)


    /*
    ################################################################################################
    PRIVATE IMPLEMENTATION: Helper
    ################################################################################################
    */

    private fun applyFragmentArguments(endpoint: FEndpoint<Destination>) {
        val fragment = endpoint.route.fragment
        fragment.arguments = endpoint.bundle
    }

    private fun findCurrentFragment(sail: KompassSail): Fragment {
        val manager = sail.manager
        val id = sail.containerId
        return manager.findFragmentById(id) ?: Fragment()
    }

    /*
    ################################################################################################
    PRIVATE: Detours
    ################################################################################################
    */


    private fun applyForwardDetour(
        endpoint: FEndpoint<Destination>, transaction: FragmentTransaction) {
        val destination = endpoint.instruction.destination
        val currentFragment = findCurrentFragment(endpoint.sail)
        val nextFragment = endpoint.route.fragment

        currentFragment.resetTransitions()
        nextFragment.resetTransitions()

        setupFragmentDetour(
            destination = destination,
            current = currentFragment,
            next = nextFragment,
            transaction = transaction)

        fragmentTransitionStack.add(
            from = currentFragment,
            to = nextFragment)
    }


    private fun applyBackwardDetour(endpoint: FEndpoint<Destination>) {
        val currentFragment = findCurrentFragment(endpoint.sail)
        fragmentTransitionStack.pop()?.to?.applyTo(currentFragment)
    }
}



