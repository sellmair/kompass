package io.sellmair.kompass.compiler

import com.squareup.kotlinpoet.*
import io.sellmair.kompass.annotation.Destination
import io.sellmair.kompass.annotation.KompassConstructor
import io.sellmair.kompass.bundle.JVMBundle
import io.sellmair.kompass.compiler.exception.DestinationConstructorException
import io.sellmair.kompass.compiler.util.kotlinTypeName
import java.io.File
import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.SourceVersion
import javax.lang.model.element.*
import javax.lang.model.util.ElementFilter

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
                .map { element: Element -> generateDestinationTools(element as TypeElement) }
                .toList()
                .createExtensions()

        return true
    }


    data class Serializer(val packageName: String, val className: String, val destinationType: TypeElement)

    private fun generateDestinationTools(element: TypeElement): Serializer {
        val packageName = processingEnv.elementUtils.getPackageOf(element).toString()
        val className = "${element.simpleName}Serializer"

        val fileUri = processingEnv.filer.createSourceFile(className, element).toUri()


        val file = FileSpec.builder(packageName, className)
                .addType(TypeSpec.classBuilder(className)
                        .addFunction(FunSpec.builder("writeToBundle")
                                .addParameter(PARAM_DESTINATION, element.asType().asTypeName())
                                .addParameter(PARAM_BUNDLE, JVMBundle::class.javaObjectType)
                                .addSerializerLogic(element)
                                .build())
                        .addFunction(FunSpec.builder("createFromBundle")
                                .addParameter(PARAM_BUNDLE, JVMBundle::class.javaObjectType)
                                .addDeserializerLogic(element)
                                .returns(element.asType().asTypeName().asNullable())
                                .build())
                        .build())
                .build()

        file.writeTo(File(fileUri))

        return Serializer(packageName, className, element)
    }

    private fun FunSpec.Builder.addDeserializerLogic(element: TypeElement): FunSpec.Builder {
        val constructor = element.getDestinationConstructor()
        this.addComment("Using constructor with params ${constructor.parameters.asSequence()
                .map { "${it.simpleName}:${it.asType()}" }.joinToString(" ")}")


        constructor.parameters.forEachIndexed { index, param ->
            val code = "val component$index = $PARAM_BUNDLE.get(\"${param.bundleArgumentName}\") " +
                    "as? ${param.asType().kotlinTypeName()} ?: return null"

            this.addCode(code).addCode("\n")
        }


        addCode("\n\n")

        val returnCode = "return ${element.asType().kotlinTypeName()} (" +
                "${constructor.parameters.indices
                        .asSequence()
                        .map { "component$it" }
                        .joinToString(", ")
                })"

        addCode(returnCode).addCode("\n")
        return this
    }


    private fun FunSpec.Builder.addSerializerLogic(element: TypeElement): FunSpec.Builder {
        val constructor = element.getDestinationConstructor()
        this.addComment("Using constructor with params ${constructor.parameters.asSequence()
                .map { "${it.simpleName}:${it.asType()}" }.joinToString(" ")}")

        constructor.parameters.forEach { field ->
            this.addSerializerLogic(element, field)
        }

        return this
    }


    private fun FunSpec.Builder.addSerializerLogic(base: TypeElement,
                                                   field: VariableElement): FunSpec.Builder {
        val fieldAccessor = ElementFilter.fieldsIn(base.enclosedElements)
                .asSequence()
                .filter { it.simpleName == field.simpleName }
                .filter { it.modifiers.contains(Modifier.PUBLIC) }
                .filter { !it.modifiers.contains(Modifier.STATIC) }
                .firstOrNull()

        val methodAccessor = ElementFilter.methodsIn(base.enclosedElements)
                .asSequence()
                .filter { it.parameters.isEmpty() }
                .filter { it.returnType == field.asType() }
                .filter { it.simpleName.toString() == "get${field.simpleName.toString().capitalize()}" }
                .firstOrNull()

        fieldAccessor ?: methodAccessor?.let {
            val code = "$PARAM_BUNDLE.put(key = \"${field.bundleArgumentName}\"," +
                    " obj = $PARAM_DESTINATION.${field.simpleName})\n"
            addCode(code)
            return this
        } ?: throw Throwable("No accessor found for ${field.simpleName}")

        return this
    }


    private fun List<Serializer>.createExtensions() {
        val extensionsFile = processingEnv.filer.createSourceFile("compiledKompassExtensions")
                .toUri()

        FileSpec.builder("io.sellmair.kompass", "compiledKompassExtensions")
                .addStaticImport("android.os", "Bundle")
                .addStaticImport("io.sellmair.kompass.bundle", "BundleAdapter")
                .also { builder -> this.forEach { builder.addDeserializerExtension(it) } }
                .build()
                .writeTo(File(extensionsFile))

    }


    fun FileSpec.Builder.addDeserializerExtension(serializer: Serializer) {
        this.addFunction(FunSpec

                .builder("as${serializer.destinationType.simpleName.toString().capitalize()}")
                .receiver(ClassName("android.os", "Bundle"))
                .returns(serializer.destinationType.asType().kotlinTypeName().asNullable())
                .addStatement("val serializer = ${ClassName(serializer.packageName, serializer.className)}()")
                .addStatement("return serializer.createFromBundle(BundleAdapter(this))")
                .build())
    }


    private fun Element.getDestinationConstructor(): ExecutableElement {
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


    private val VariableElement.bundleArgumentName: String
        get() = "ARG_${this.simpleName}"


    companion object {
        const val PARAM_DESTINATION = "destination"
        const val PARAM_BUNDLE = "bundle"
    }
}