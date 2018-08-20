@file:Suppress("unused")

package io.sellmair.kompass.internal.pipe

import android.os.Bundle
import io.sellmair.kompass.KompassRoute
import io.sellmair.kompass.KompassSail

typealias Container = Map<String, Any>

internal class Payload<Destination, Stage> private constructor(
    private val container: Container) {

    enum class Member(val key: String) {
        INSTRUCTION("instruction"),
        SAIL("sail"),
        BUNDLE("bundle"),
        ROUTE("route")
    }


    /*
    ################################################################################################
    HELPER
    ################################################################################################
    */
    operator fun get(member: Member): Any? = this.container[member.key]

    operator fun <T> set(member: Member, value: Any): Payload<Destination, T> {
        val newContainer = this.container.plus(member.key to value)
        return Payload(newContainer)
    }

    @Suppress("UNCHECKED_CAST")
    companion object {

        /*
        ############################################################################################
        PSEUDO CONSTRUCTOR
        ############################################################################################
        */
        operator fun <Destination : Any> invoke(): Payload<Destination, Unit> {
            return Payload(mapOf())
        }

        operator fun <Destination : Any> invoke(instruction: Instruction<Destination>):
            Payload<Destination, Stage.Pending> {
            return invoke<Destination>().pending(instruction)
        }

        /*
        ############################################################################################
        READ COMPANIONS
        ############################################################################################
        */

        fun <Destination : Any>
            instructionOf(payload: Payload<Destination, out ReadInstruction>):
            Instruction<Destination> {
            return payload[Member.INSTRUCTION] as? Instruction<Destination>
                ?: throwAccessException(payload, Member.INSTRUCTION)
        }

        fun bundleOf(payload: Payload<*, out ReadBundle>):
            Bundle {
            return payload[Member.BUNDLE] as? Bundle
                ?: throwAccessException(payload, Member.BUNDLE)
        }


        fun sailOf(payload: Payload<*, out ReadSail>):
            KompassSail {
            return payload[Member.SAIL] as? KompassSail
                ?: throwAccessException(payload, Member.SAIL)
        }


        fun routeOf(payload: Payload<*, out ReadRoute>):
            KompassRoute {
            return payload[Member.ROUTE] as? KompassRoute
                ?: throwAccessException(payload, Member.ROUTE)
        }


        fun fragmentRouteOf(payload: Payload<*, out ReadFragmentRoute>):
            KompassRoute.Fragment {
            return payload[Member.ROUTE] as? KompassRoute.Fragment
                ?: throwAccessException(payload, Member.ROUTE)
        }


        fun activityRouteOf(payload: Payload<*, out ReadActivityRoute>):
            KompassRoute.Activity<*> {
            return payload[Member.ROUTE] as? KompassRoute.Activity<*>
                ?: throwAccessException(payload, Member.ROUTE)
        }


        fun viewRouteOf(payload: Payload<*, out ReadViewRoute>):
            KompassRoute.View<*> {
            return payload[Member.ROUTE] as? KompassRoute.View<*>
                ?: throwAccessException(payload, Member.ROUTE)
        }


        /*
        ############################################################################################
        COMPANION HELPER
        #############################################################################################
        */

        private fun throwAccessException(payload: Payload<*, *>, member: Member): Nothing {
            throw KompassPayloadAccessException(payload.container, member.key)
        }
    }
}


internal class KompassPayloadAccessException(container: Map<String, Any>, key: String) :
    Exception("""
    Tried to access missing key $key from payload.
    Present Keys: ${container.keys.joinToString(", ")}
    """.trimIndent().trim())


/*
################################################################################################
READ RIGHTS
################################################################################################
*/

internal interface ReadInstruction

internal interface ReadBundle

internal interface ReadSail

internal interface ReadRoute

internal interface ReadFragmentRoute

internal interface ReadActivityRoute

internal interface ReadViewRoute


/*
################################################################################################
READ EXTENSIONS
################################################################################################
*/

internal val <Destination : Any>
    Payload<Destination, out ReadInstruction>.instruction: Instruction<Destination>
    get() = Payload.instructionOf(this)

internal val Payload<*, out ReadBundle>.bundle: Bundle get() = Payload.bundleOf(this)

internal val Payload<*, out ReadSail>.sail: KompassSail get() = Payload.sailOf(this)

internal val Payload<*, out ReadRoute>.route: KompassRoute get() = Payload.routeOf(this)

@get:JvmName("fragmentRoute")
internal val Payload<*, out ReadFragmentRoute>.route: KompassRoute.Fragment
    get() = Payload.fragmentRouteOf(this)

@get:JvmName("activityRoute")
internal val Payload<*, out ReadActivityRoute>.route: KompassRoute.Activity<*>
    get() = Payload.activityRouteOf(this)

@get:JvmName("viewRoute")
internal val Payload<*, out ReadViewRoute>.route: KompassRoute.View<*>
    get() = Payload.viewRouteOf(this)


/*
################################################################################################
STAGES
################################################################################################
*/

internal interface Stage {

    interface Pending : Stage, ReadInstruction

    interface Craned : Stage, ReadInstruction, ReadBundle

    interface Sailed : Stage, ReadInstruction, ReadBundle, ReadSail

    interface Routed : Stage, ReadInstruction, ReadSail, ReadBundle, ReadRoute

    interface Endpoint : Stage, ReadInstruction, ReadSail, ReadBundle {

        interface Fragment : Endpoint, ReadFragmentRoute

        interface Activity : Endpoint, ReadActivityRoute

        interface View : Endpoint, ReadViewRoute
    }
}


/*
################################################################################################
TRANSITIONS
################################################################################################
*/

internal fun <Destination : Any>
    Payload<Destination, Unit>.pending(instruction: Instruction<Destination>):
    Payload<Destination, Stage.Pending> {
    return this.set(Payload.Member.INSTRUCTION, instruction)
}

internal fun <Destination : Any> Payload<Destination, Stage.Pending>.craned(bundle: Bundle):
    Payload<Destination, Stage.Craned> {
    return this.set(Payload.Member.BUNDLE, bundle)
}


internal fun <Destination : Any> Payload<Destination, Stage.Craned>.sailed(sail: KompassSail):
    Payload<Destination, Stage.Sailed> {
    return this.set(Payload.Member.SAIL, sail)
}


internal fun <Destination : Any> Payload<Destination, Stage.Sailed>.routed(route: KompassRoute):
    Payload<Destination, Stage.Routed> {
    return this.set(Payload.Member.ROUTE, route)
}


/*
################################################################################################
ENDPOINT SAFE TRANSITIONS
################################################################################################
*/

internal fun <Destination> Payload<Destination, Stage.Routed>.fragmentEndpoint():
    Payload<Destination, Stage.Endpoint.Fragment>? {
    return if (this.route is KompassRoute.Fragment) {
        this.set(Payload.Member.ROUTE, route)
    } else null
}

internal fun <Destination> Payload<Destination, Stage.Routed>.activityEndpoint():
    Payload<Destination, Stage.Endpoint.Activity>? {
    return if (this.route is KompassRoute.Activity<*>) {
        this.set(Payload.Member.ROUTE, route)
    } else null
}

internal fun <Destination> Payload<Destination, Stage.Routed>.viewEndpoint():
    Payload<Destination, Stage.Endpoint.View>? {
    return if (this.route is KompassRoute.View<*>) {
        this.set(Payload.Member.ROUTE, route)
    } else null
}