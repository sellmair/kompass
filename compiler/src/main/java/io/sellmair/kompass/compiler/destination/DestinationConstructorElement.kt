package io.sellmair.kompass.compiler.destination

import javax.lang.model.element.ExecutableElement
import javax.lang.model.util.ElementFilter

/*
################################################################################################
INTERNAL API
################################################################################################
*/

internal class DestinationConstructorElement(private val source: ExecutableElement) :
    ExecutableElement by source {


    override fun getParameters(): MutableList<DestinationConstructorParameter> {
        return source.parameters
            .asSequence()
            .map(::DestinationConstructorParameter)
            .toMutableList()
    }


    companion object
}


internal fun DestinationConstructorElement.Companion.from(element: DestinationElement):
    DestinationConstructorElement {
    val constructors = ElementFilter.constructorsIn(element.enclosedElements)
    val constructor = constructors
        .asSequence()
        /*
        Sorting by parameters.size is desired, because this will
        pick the constructor that holds as much information about the destination
        as possible
         */
        .sortedByDescending { it.parameters.size }
        .filter(element::hasAccessorsForConstructor)
        .firstOrNull() ?: throw NoSuitableConstructorException(element)

    return DestinationConstructorElement(constructor)
}


private fun DestinationElement.hasAccessorsForConstructor(constructor: ExecutableElement): Boolean {
    return constructor.parameters.all { parameter -> this.accessorFor(parameter) != null }
}

internal class NoSuitableConstructorException(element: DestinationElement) : Exception("""

Destination: ${element.simpleName} (${element.qualifiedName})
has no suitable constructor.
Please make sure, that at least one constructor exists where all parameters can be
retrieved from the destination by name.

""".trimIndent())

