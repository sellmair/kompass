package io.sellmair.kompass.compiler

import com.squareup.javapoet.JavaFile
import com.squareup.javapoet.TypeSpec
import com.squareup.kotlinpoet.FileSpec
import io.sellmair.kompass.annotation.Destination
import io.sellmair.kompass.compiler.crane.CraneBuilder
import io.sellmair.kompass.compiler.deserialize.DeserializeMethodBuilder
import io.sellmair.kompass.compiler.extension.ExtensionBuilder
import io.sellmair.kompass.compiler.map.MapBuilder
import io.sellmair.kompass.compiler.serialize.SerializeMethodBuilder
import java.io.File
import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.SourceVersion
import javax.lang.model.element.Modifier.FINAL
import javax.lang.model.element.Modifier.PUBLIC
import javax.lang.model.element.TypeElement


/**
 * Created by sebastiansellmair on 09.12.17.
 */
class DestinationProcessor : AbstractProcessor() {


    override fun getSupportedAnnotationTypes() = mutableSetOf(Destination::class.java.name)
    override fun getSupportedSourceVersion() = SourceVersion.latest()!!
    override fun process(set: MutableSet<out TypeElement>, environment: RoundEnvironment): Boolean {
        val destinations = environment.getElementsAnnotatedWith(Destination::class.java)
        if (destinations.isEmpty()) return false


        destinations.asSequence()
                .mapNotNull { it as? TypeElement }
                .onEach { element -> generateSerializer(element) }
                .onEach { element -> generateExtensions(element) }
                .toList()
                .also { elements -> generateCrane(elements) }
                .also { elements -> generateMap(elements) }

        return true
    }


    private fun generateSerializer(base: TypeElement) {
        val packageName = base.serializerPackageName(processingEnv)
        val className = base.serializerClassName()

        val serializerMethodBuilder = SerializeMethodBuilder()
        val deserializeMethodBuilder = DeserializeMethodBuilder()

        val type = TypeSpec.classBuilder(className)
                .addModifiers(PUBLIC, FINAL)
                .let { serializerMethodBuilder.addSerializeMethod(processingEnv, it, base) }
                .let { deserializeMethodBuilder.addDeserializeMethod(processingEnv, it, base) }
                .build()

        JavaFile.builder(packageName, type).build()
                .writeTo(processingEnv.filer)

    }


    private fun generateExtensions(base: TypeElement) {
        val packageName = "io.sellmair.kompass"
        val fileName = "${base.qualifiedName}Extensions"
        val fileUri = processingEnv.filer.createSourceFile(fileName, base).toUri()
        val fileSpec = FileSpec.builder(packageName, fileName)

        ExtensionBuilder().buildSerializerFunctions(processingEnv, fileSpec, base)
        fileSpec.build().writeTo(File(fileUri))
    }


    private fun generateCrane(elements: List<TypeElement>) {
        val packageName = "io.sellmair.kompass"
        val fileName = "AutoCrane"
        val fileUri = processingEnv.filer.createSourceFile(fileName, *elements.toTypedArray()).toUri()
        val fileSpec = FileSpec.builder(packageName, fileName)
        CraneBuilder().buildCraneType(processingEnv, fileSpec, elements)
        fileSpec.build().writeTo(File(fileUri))
    }


    private fun generateMap(elements: List<TypeElement>) {
        val packageName = "io.sellmair.kompass"
        val fileName = "AutoMap"
        val fileUri = processingEnv.filer.createSourceFile(fileName, *elements.toTypedArray()).toUri()
        val fileSpec = FileSpec.builder(packageName, fileName)
        MapBuilder().buildMapType(processingEnv, fileSpec, elements)
        fileSpec.build().writeTo(File(fileUri))
    }

}