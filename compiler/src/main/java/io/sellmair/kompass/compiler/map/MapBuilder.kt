package io.sellmair.kompass.compiler.map

import com.squareup.kotlinpoet.*
import io.sellmair.kompass.compiler.util.destinationTarget
import javax.annotation.processing.ProcessingEnvironment
import javax.lang.model.element.TypeElement

/**
 * Created by sebastiansellmair on 03.01.18.
 */
interface MapBuilder {
    fun buildMapType(environment: ProcessingEnvironment, fileSpec: FileSpec.Builder, elements: List<TypeElement>)
}


class MapBuilderImpl : MapBuilder {
    override fun buildMapType(environment: ProcessingEnvironment, fileSpec: FileSpec.Builder, elements: List<TypeElement>) {


        val classBuilder = TypeSpec.classBuilder("AutoMap")
                .addTypeVariable(TypeVariableName("Destination: Any"))
                .addSuperinterface(ClassName("io.sellmair.kompass", "KompassMap<Destination>"))


        val getFunction = FunSpec.builder("get")
                .addModifiers(KModifier.OVERRIDE)
                .addParameter("destination", TypeVariableName("Destination"))
                .returns(ClassName("io.sellmair.kompass", "KompassRoute"))


        getFunction.beginControlFlow("return when(destination)")
        elements.asSequence()
                .mapNotNull { element -> element.destinationTarget?.let { element to it } }
                .forEach { (element, target) ->
                    val targetElement = environment.typeUtils.asElement(target) as TypeElement
                    fileSpec.addAliasedImport(element.asClassName(), element.asClassName().simpleName())
                    fileSpec.addAliasedImport(targetElement.asClassName(), targetElement.asClassName().simpleName())



                    getFunction.addStatement("" +
                            "is ${element.asClassName().simpleName()} -> " +
                            targetElement.asClassName().simpleName() +
                            (if (targetElement.isActivity(environment)) "::class" else "()") +
                            ".asRoute()")
                }

        fileSpec.addStaticImport("io.sellmair.kompass.exception", "MissingMapElementException")
        getFunction.addStatement("else -> throw MissingMapElementException(destination)")

        getFunction.endControlFlow()


        classBuilder.addFunction(getFunction.build())
        fileSpec.addType(classBuilder.build())
    }


    private fun TypeElement.isActivity(environment: ProcessingEnvironment): Boolean {
        val activityType = environment.elementUtils.getTypeElement("android.app.Activity")
        return environment.typeUtils.isAssignable(this.asType(), activityType.asType())
    }

}

