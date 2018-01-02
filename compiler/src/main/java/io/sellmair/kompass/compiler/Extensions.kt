package io.sellmair.kompass.compiler

import io.sellmair.kompass.annotation.KompassConstructor
import io.sellmair.kompass.compiler.exception.DestinationConstructorException
import javax.annotation.processing.ProcessingEnvironment
import javax.lang.model.element.Element
import javax.lang.model.element.ExecutableElement
import javax.lang.model.util.ElementFilter

/**
 * Created by sebastiansellmair on 10.12.17.
 */
fun Element.getKompassConstructor(): ExecutableElement {
    val constructors = ElementFilter.constructorsIn(this.enclosedElements)
            .asSequence()
            .toList()

    return if (constructors.size == 1) constructors.first()
    else constructors
            .filter { it.getAnnotation(KompassConstructor::class.java) != null }
            .toList().let {
        if (it.size == 1) it.first() else throw DestinationConstructorException()
    }
}

fun Element.serializerClassName(): String {
    return "${this.simpleName}Serializer"
}

fun Element.serializerPackageName(environment: ProcessingEnvironment): String {
    return environment.elementUtils.getPackageOf(this).toString()
}