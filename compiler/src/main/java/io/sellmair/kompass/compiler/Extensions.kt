package io.sellmair.kompass.compiler

import io.sellmair.kompass.compiler.constructor.ConstructorSelector
import javax.annotation.processing.ProcessingEnvironment
import javax.lang.model.element.Element
import javax.lang.model.element.ExecutableElement

/**
 * Created by sebastiansellmair on 10.12.17.
 */
fun Element.getKompassConstructor(): ExecutableElement {
    return ConstructorSelector.create().getKompassConstructor(this)
}

fun Element.serializerClassName(): String {
    return "${this.simpleName}Serializer"
}

fun Element.serializerPackageName(environment: ProcessingEnvironment): String {
    return environment.elementUtils.getPackageOf(this).toString()
}