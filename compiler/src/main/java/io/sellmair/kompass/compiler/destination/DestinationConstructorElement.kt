package io.sellmair.kompass.compiler.destination

import javax.lang.model.element.ExecutableElement
import javax.lang.model.util.ElementFilter

class DestinationConstructorElement(source: ExecutableElement) : ExecutableElement by source {
    companion object
}


fun DestinationConstructorElement.Companion.from(element: DestinationElement):
    DestinationConstructorElement {
    val constructors = ElementFilter.constructorsIn(element.enclosedElements)

    // TODO get a suitable constructor
    return DestinationConstructorElement(constructors.first())
}

