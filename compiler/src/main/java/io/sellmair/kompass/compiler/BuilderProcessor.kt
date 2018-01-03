package io.sellmair.kompass.compiler

import com.squareup.kotlinpoet.FileSpec
import io.sellmair.kompass.annotation.Destination
import io.sellmair.kompass.annotation.Detour
import io.sellmair.kompass.compiler.builder.BuilderExtensionsBuilder
import java.io.File
import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.SourceVersion
import javax.lang.model.element.Element
import javax.lang.model.element.TypeElement

/**
 * Created by sebastiansellmair on 03.01.18.
 */
class BuilderProcessor : AbstractProcessor() {
    override fun getSupportedAnnotationTypes()
            = mutableSetOf(Destination::class.java.name, Detour::class.java.name)

    override fun getSupportedSourceVersion() = SourceVersion.latest()!!
    override fun process(p0: MutableSet<out TypeElement>?, environment: RoundEnvironment): Boolean {

        val elements = mutableListOf<Element>()
        environment.getElementsAnnotatedWith(Destination::class.java).also {
            elements.addAll(it)
        }
        environment.getElementsAnnotatedWith(Detour::class.java).also {
            elements.addAll(it)
        }


        val packageName = "io.sellmair.kompass"
        val fileName = "KompassBuilderExtensions"
        val fileUri = processingEnv.filer.createSourceFile(fileName, *elements.toTypedArray()).toUri()
        val fileSpec = FileSpec.builder(packageName, fileName)
        BuilderExtensionsBuilder().buildExtensions(processingEnv, fileSpec)
        fileSpec.build().writeTo(File(fileUri))

        return true
    }

}