package io.sellmair.kompass.compiler

import com.squareup.kotlinpoet.FileSpec
import io.sellmair.kompass.annotation.Detour
import io.sellmair.kompass.compiler.pilot.PilotBuilder
import java.io.File
import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.SourceVersion
import javax.lang.model.element.TypeElement

/**
 * Created by sebastiansellmair on 03.01.18.
 */
class DetourProcessor : AbstractProcessor() {
    override fun getSupportedAnnotationTypes() = mutableSetOf(Detour::class.java.name)
    override fun getSupportedSourceVersion() = SourceVersion.latest()!!

    override fun process(set: MutableSet<out TypeElement>, environment: RoundEnvironment): Boolean {
        val detours = environment.getElementsAnnotatedWith(Detour::class.java)
        if (detours.isEmpty()) return false

        detours.asSequence()
                .mapNotNull { it as TypeElement }
                .toList()
                .also { elements -> generateDetourPilot(elements) }
        return true
    }

    private fun generateDetourPilot(elements: List<TypeElement>) {
        val packageName = "io.sellmair.kompass"
        val fileName = "AutoDetourPilot"
        val fileUri = processingEnv.filer.createSourceFile(fileName, *elements.toTypedArray()).toUri()
        val fileSpec = FileSpec.builder(packageName, fileName)
        PilotBuilder().buildPilotType(processingEnv, fileSpec, elements)
        fileSpec.build().writeTo(File(fileUri))
    }
}