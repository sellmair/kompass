package io.sellmair.kompass.internal

import android.os.Bundle
import android.support.v4.app.Fragment
import io.sellmair.kompass.internal.precondition.Precondition
import io.sellmair.kompass.internal.precondition.requireMainThread
import java.util.*


internal interface Transitions {
    val enterT: Any?
    val exitT: Any?
    val reenterT: Any?
    val returnT: Any?

    fun applyTo(fragment: Fragment) {
        fragment.enterTransition = enterT
        fragment.exitTransition = exitT
        fragment.reenterTransition = reenterT
        fragment.returnTransition = returnT
    }
}

private data class IdentifiedTransitions(
    val id: FragmentId,
    override val enterT: Any?,
    override val exitT: Any?,
    override val reenterT: Any?,
    override val returnT: Any?) : Transitions {
    constructor(fragment: Fragment) : this(
        id = FragmentId(fragment.javaClass, fragment.arguments),
        enterT = fragment.enterTransition,
        exitT = fragment.exitTransition,
        reenterT = fragment.reenterTransition,
        returnT = fragment.returnTransition
    )
}

private data class FragmentId(
    val clazz: Class<*>,
    val arguments: Bundle?) {
    fun matches(fragment: Fragment): Boolean {
        if (!clazz.isInstance(fragment)) return false

        val otherArguments = fragment.arguments
        if (arguments == null && otherArguments == null) return true
        if (arguments == null) return false
        if (otherArguments == null) return false
        return equalBundles(arguments, otherArguments)
    }
}

internal interface Forward {
    val from: Transitions
    val to: Transitions
}

private class IdentifiedForward(
    override val from: IdentifiedTransitions,
    override val to: IdentifiedTransitions) : Forward

internal interface FragmentTransitionStack {
    fun retain(fragment: Fragment)

    fun add(from: Fragment, to: Fragment)

    fun pop(): Forward?

    companion object {
        fun create(): FragmentTransitionStack = FragmentTransitionsStackImpl()
    }
}

private class FragmentTransitionsStackImpl : FragmentTransitionStack {
    /*
    ################################################################################################
    API
    ################################################################################################
    */

    override fun retain(fragment: Fragment) {
        Precondition.requireMainThread()
        val transitions = stack.asSequence()
            .flatMap { sequenceOf(it.from, it.to) }
            .lastOrNull { identifiedTransitions -> identifiedTransitions.id.matches(fragment) }

        transitions?.applyTo(fragment)
    }

    override fun add(from: Fragment, to: Fragment) {
        Precondition.requireMainThread()
        stack.add(IdentifiedForward(
            from = IdentifiedTransitions(from),
            to = IdentifiedTransitions(to)))

    }

    override fun pop(): Forward? {
        Precondition.requireMainThread()
        return stack.pop()
    }

    /*
    ################################################################################################
    PRIVATE IMPLEMENTATION
    ################################################################################################
    */

    private val stack = Stack<IdentifiedForward>()

}


fun equalBundles(one: Bundle, two: Bundle): Boolean {
    if (one.size() != two.size())
        return false

    val setOne = HashSet(one.keySet())
    setOne.addAll(two.keySet())
    var valueOne: Any?
    var valueTwo: Any?

    for (key in setOne) {
        if (!one.containsKey(key) || !two.containsKey(key))
            return false

        valueOne = one.get(key)
        valueTwo = two.get(key)
        if (valueOne is Bundle && valueTwo is Bundle &&
            !equalBundles(valueOne, valueTwo)) {
            return false
        } else if (valueOne == null) {
            if (valueTwo != null)
                return false
        } else if (valueOne != valueTwo)
            return false
    }

    return true
}