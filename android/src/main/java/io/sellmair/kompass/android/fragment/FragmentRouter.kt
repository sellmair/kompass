package io.sellmair.kompass.android.fragment

import android.os.Bundle
import io.sellmair.kompass.android.fragment.dsl.FragmentRouterBuilder
import io.sellmair.kompass.android.fragment.dsl.FragmentRouterDsl
import io.sellmair.kompass.android.fragment.internal.FragmentContainerLifecycle
import io.sellmair.kompass.android.fragment.internal.FragmentElementImpl
import io.sellmair.kompass.android.fragment.internal.FragmentRouterConfiguration
import io.sellmair.kompass.android.utils.mainThread
import io.sellmair.kompass.android.utils.requireMainThread
import io.sellmair.kompass.core.Route
import io.sellmair.kompass.core.Router
import io.sellmair.kompass.core.RoutingStack
import io.sellmair.kompass.core.RoutingStackManipulation


class FragmentRouter<T : Route> internal constructor(
    override val fragmentMap: FragmentMap<T>,
    override val fragmentRouteStorage: FragmentRouteStorage<T>,
    override val fragmentRoutingStackBundler: FragmentRoutingStackBundler<T>,
    private val fragmentTransition: FragmentTransition,
    private val fragmentStackPatcher: FragmentStackPatcher,
    fragmentContainerLifecycleFactory: FragmentContainerLifecycle.Factory,
    initialStack: RoutingStack<T> = RoutingStack.empty()
) :
    Router<T>,
    FragmentRouterConfiguration<T> {

    /**
     * Represents the whole state of this router
     */
    internal sealed class State<T : Route> {

        abstract val stack: RoutingStack<T>

        data class Attached<T : Route>(
            override val stack: RoutingStack<T>,
            val container: FragmentContainer
        ) : State<T>()

        data class Detached<T : Route>(
            override val stack: RoutingStack<T>,
            val pendingStack: RoutingStack<T>
        ) : State<T>()
    }


    internal val fragmentContainerLifecycle: FragmentContainerLifecycle = fragmentContainerLifecycleFactory(this)


    private var _state: State<T> = State.Detached(stack = RoutingStack.empty(), pendingStack = initialStack)

    private var state: State<T>
        set(newState) {
            requireMainThread()
            val oldState = _state
            _state = newState
            onStateChanged(oldState = oldState, newState = newState)
        }
        get() {
            requireMainThread()
            return _state
        }


    override fun execute(instruction: RoutingStackManipulation<T>) = mainThread {
        executeImmediate(instruction)
    }


    private fun executeImmediate(instruction: RoutingStackManipulation<T>): RoutingStack<T> {
        requireMainThread()
        state = state.nextState(instruction)
        return when (val state = state) {
            is State.Attached<T> -> state.stack
            is State.Detached<T> -> state.pendingStack
        }
    }


    internal fun attachContainer(container: FragmentContainer) {
        requireMainThread()
        val state = this.state
        check(state is State.Detached<T>)
        this.state = State.Attached(state.pendingStack, container)
    }

    internal fun detachContainer() {
        requireMainThread()
        val state = this.state as? State.Attached<T> ?: return
        this.state = State.Detached(
            stack = state.stack,
            pendingStack = state.stack
        )
    }

    internal fun saveState(outState: Bundle) {
        requireMainThread()
        val stack = when (val state = state) {
            is State.Attached<T> -> state.stack
            is State.Detached<T> -> state.pendingStack
        }

        fragmentRoutingStackBundler.run {
            stack.saveTo(outState)
        }
    }

    internal fun restoreState(outState: Bundle) {
        requireMainThread()
        val stack = fragmentRoutingStackBundler.run { outState.restore() } ?: return
        _state = when (val state = state) {
            is State.Attached -> State.Attached(stack = stack, container = state.container)
            is State.Detached -> State.Detached(stack = stack, pendingStack = stack)
        }
    }

    private fun State<T>.nextState(instruction: RoutingStackManipulation<T>): State<T> = when (this) {
        is State.Attached -> copy(stack = stack.instruction())
        is State.Detached -> copy(pendingStack = pendingStack.instruction())
    }


    private fun onStateChanged(oldState: State<T>, newState: State<T>) {
        if (newState is State.Attached<T>) {
            apply(oldState, newState)
        }
    }

    private fun apply(oldState: State<T>, newState: State.Attached<T>) {
        if (oldState.stack.elements == newState.stack.elements) {
            return
        }

        fragmentStackPatcher(fragmentTransition, newState.container, oldState.stack, prepareFragmentStack(newState))
    }

    private fun prepareFragmentStack(state: State.Attached<T>): FragmentRoutingStack<T> {
        val factory = FragmentElementImpl.Factory(this, state.container)
        return FragmentRoutingStack(state.stack.elements, factory)
    }


    companion object Factory {
        @FragmentRouterDsl
        inline operator fun <reified T : Route> invoke(init: FragmentRouterBuilder<T>.() -> Unit): FragmentRouter<T> {
            return FragmentRouterBuilder(T::class).also(init).build()
        }
    }
}


