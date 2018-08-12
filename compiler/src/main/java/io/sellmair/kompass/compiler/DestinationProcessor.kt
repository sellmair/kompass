package io.sellmair.kompass.compiler

import io.sellmair.kompass.annotation.Destination
import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.SourceVersion
import javax.lang.model.element.TypeElement

class DestinationProcessor : AbstractProcessor() {
    override fun getSupportedAnnotationTypes() = mutableSetOf(Destination::class.java.name)
    override fun getSupportedSourceVersion() = SourceVersion.latest()!!

    override fun process(elements: MutableSet<out TypeElement>?,
                         roundEnvironment: RoundEnvironment?): Boolean {
        return true
    }

}