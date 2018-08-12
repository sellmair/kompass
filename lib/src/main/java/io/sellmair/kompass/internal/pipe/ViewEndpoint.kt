package io.sellmair.kompass.internal.pipe

import android.content.Context
import android.view.View
import android.view.ViewGroup
import io.sellmair.kompass.KeyLessBackStack
import java.lang.reflect.Constructor
import kotlin.reflect.KClass

private typealias VEndpoint<Destination> = Payload<Destination, Stage.Endpoint.View>

internal class ViewEndpoint<Destination : Any>(
    backStack: KeyLessBackStack) :
    InstructionEndpoint<Destination>,
    Handleable<Unit> by Handleable.delegate(),
    KeyLessBackStack by backStack {

    override fun invoke(payload: Payload<Destination, Stage.Routed>) {
        val endpoint = payload.viewEndpoint()
        if (endpoint != null) {
            route(endpoint)
        }
    }

    private fun route(endpoint: VEndpoint<Destination>) {
        when (endpoint.instruction) {
            is Instruction.StartAt -> startAt(endpoint)
            is Instruction.NavigateTo -> navigateTo(endpoint)
            is Instruction.BeamTo -> beamTo(endpoint)
        }
    }


    private fun startAt(endpoint: VEndpoint<Destination>) {
        val view = instantiateView(endpoint)
        val sail = endpoint.sail
        val container = sail.activity.findViewById<ViewGroup>(sail.containerId)
        container.removeAllViews()
        container.addView(view)
    }


    private fun navigateTo(endpoint: VEndpoint<Destination>) {
        val view = instantiateView(endpoint)
        val sail = endpoint.sail
        val container = sail.activity.findViewById<ViewGroup>(sail.containerId)

        val oldView = container.getChildAt(0)

        if (oldView != null) {
            container.removeView(oldView)
            onBack {
                container.removeViewAt(0)
                container.addView(oldView)
            }
        }

        container.addView(view, 0)
    }

    private fun beamTo(endpoint: VEndpoint<Destination>) {
        val view = instantiateView(endpoint)
        val sail = endpoint.sail
        val container = sail.activity.findViewById<ViewGroup>(sail.containerId)

        val oldView = container.getChildAt(0)
        if (oldView != null) {
            container.removeView(oldView)
        }

        container.addView(view, 0)
    }


    private fun instantiateView(endpoint: VEndpoint<Destination>): View {
        val activity = endpoint.sail.activity
        val destination = endpoint.instruction.destination

        val viewClazz = endpoint.route.clazz
        return findContextDestinationConstructor(viewClazz, endpoint)
            .newInstance(activity, destination) as View
    }

    private fun findContextDestinationConstructor(
        viewClass: KClass<*>, endpoint: VEndpoint<Destination>): Constructor<*> {

        val destination = endpoint.instruction.destination

        val constructors = viewClass.java.constructors

        fun Constructor<*>.hasTwoParams() = parameterTypes.size == 2

        fun Constructor<*>.firstParamIsContext() =
            parameterTypes[0].isAssignableFrom(Context::class.java)

        fun Constructor<*>.secondParamIsDestination() =
            parameterTypes[1].isAssignableFrom(destination::class.java)


        return constructors.asSequence()
            .filter(Constructor<*>::hasTwoParams)
            .filter(Constructor<*>::firstParamIsContext)
            .filter(Constructor<*>::secondParamIsDestination)
            .firstOrNull() ?: throw KompassViewConstructorException(viewClass, destination)
    }

}


private class KompassViewConstructorException(clazz: KClass<*>, destination: Any) : Exception(
    """
    Failed to instantiate view of type ${clazz.java.simpleName}.
    Expected constructor: ${clazz.java.simpleName}(Context, ${destination::class.java.simpleName}).
    """.trimIndent())