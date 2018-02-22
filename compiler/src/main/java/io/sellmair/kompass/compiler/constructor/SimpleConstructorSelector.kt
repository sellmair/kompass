package io.sellmair.kompass.compiler.constructor

import io.sellmair.kompass.annotation.KompassConstructor
import io.sellmair.kompass.compiler.exception.DestinationConstructorException
import javax.lang.model.element.Element
import javax.lang.model.element.ExecutableElement
import javax.lang.model.util.ElementFilter

/**
 * Created by sebastiansellmair on 09.02.18.
 */

class SimpleConstructorSelector : ConstructorSelector {
    override fun getKompassConstructor(element: Element): ExecutableElement {
        val constructors = ElementFilter.constructorsIn(element.enclosedElements)
            .asSequence()
            .toList()

        return if (constructors.size == 1) constructors.first()
        else constructors
            .filter { it.getAnnotation(KompassConstructor::class.java) != null }
            .toList().let {
            if (it.size == 1) it.first() else throw DestinationConstructorException()
        }
    }
}