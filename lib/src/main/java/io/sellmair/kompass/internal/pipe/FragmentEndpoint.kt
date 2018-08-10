package io.sellmair.kompass.internal.pipe

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import io.sellmair.kompass.KeyLessBackStack
import io.sellmair.kompass.KompassRoute
import io.sellmair.kompass.KompassSail
import io.sellmair.kompass.internal.DetourRegistry
import io.sellmair.kompass.internal.pipe.instruction.BundledRoutedSailedInstruction
import io.sellmair.kompass.internal.pipe.instruction.Instruction
import io.sellmair.kompass.internal.pipe.instruction.InstructionEndpoint

internal class FragmentEndpoint<Destination : Any>(
    backStack: KeyLessBackStack,
    detourRegistry: DetourRegistry) :
    InstructionEndpoint<Destination>,
    Handleable<Unit> by Handleable.delegate(),
    KeyLessBackStack by backStack,
    DetourRegistry by detourRegistry {

    override fun invoke(instruction: BundledRoutedSailedInstruction<Destination>) {
        val route = instruction.route
        if (route is KompassRoute.Fragment) {
            this.route(Endpoint(
                bundle = instruction.bundle,
                route = route,
                sail = instruction.sail,
                instruction = instruction.instruction))
        }
    }

    private inner class Endpoint(
        val bundle: Bundle,
        val route: KompassRoute.Fragment,
        val sail: KompassSail,
        val instruction: Instruction<Destination>)

    private fun route(endpoint: Endpoint) {
        applyFragmentArguments(endpoint)

        when (endpoint.instruction) {
            is Instruction.StartAt -> startAt(endpoint)
            is Instruction.NavigateTo -> navigateTo(endpoint)
            is Instruction.BeamTo -> beamTo(endpoint)
        }
    }

    private fun startAt(endpoint: Endpoint) {
        (this as KeyLessBackStack).clear()

        val manager = endpoint.sail.manager
        val containerId = endpoint.sail.containerId
        val fragment = endpoint.route.fragment

        val transaction = manager.beginTransaction()

        applyDetour(endpoint, transaction)

        transaction.replace(containerId, fragment)
        transaction.commit()

    }

    private fun navigateTo(endpoint: Endpoint) {
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

    private fun beamTo(endpoint: Endpoint) {
        backImmediate()

        val manager = endpoint.sail.manager
        val containerId = endpoint.sail.containerId
        val fragment = endpoint.route.fragment

        val transaction = manager.beginTransaction()
        applyDetour(endpoint, transaction)

        transaction.replace(containerId, fragment)
        transaction.commit()

        onBack { manager.popBackStackImmediate() }
    }


    private fun applyFragmentArguments(endpoint: Endpoint) {
        val fragment = endpoint.route.fragment
        fragment.arguments = endpoint.bundle
    }


    private fun applyDetour(endpoint: Endpoint, transaction: FragmentTransaction) {
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