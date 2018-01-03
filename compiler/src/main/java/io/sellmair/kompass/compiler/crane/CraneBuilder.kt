package io.sellmair.kompass.compiler.crane

import com.squareup.kotlinpoet.*
import io.sellmair.kompass.compiler.serializerClassName
import io.sellmair.kompass.compiler.serializerPackageName
import javax.annotation.processing.ProcessingEnvironment
import javax.lang.model.element.TypeElement

/**
 * Created by sebastiansellmair on 02.01.18.
 */
interface CraneBuilder {
    fun buildCraneType(environment: ProcessingEnvironment, fileSpec: FileSpec.Builder, elements: List<TypeElement>)
}

class CraneBuilderImpl : CraneBuilder {
    override fun buildCraneType(environment: ProcessingEnvironment, fileSpec: FileSpec.Builder, elements: List<TypeElement>) {
        val classBuilder = TypeSpec.classBuilder("AutoCrane")
                .addTypeVariable(TypeVariableName("Destination: Any"))
                .addSuperinterface(ClassName("io.sellmair.kompass", "KompassCrane<Destination>"))

        val bundleFunction = FunSpec.builder("bundle")
                .addModifiers(KModifier.OVERRIDE)
                .returns(ClassName("android.os", "Bundle"))
                .addParameter("destination", TypeVariableName("Destination"))


        elements.forEach { element ->
            fileSpec.addAliasedImport(element.asClassName(), element.asClassName().simpleName())
            fileSpec.addAliasedImport(ClassName(element.serializerPackageName(environment),
                    element.serializerClassName()),
                    element.serializerClassName())
            bundleFunction.beginControlFlow("if(destination is ${element.simpleName})")
            bundleFunction.addStatement("val bundle = Bundle()")
            bundleFunction.addStatement("${element.serializerClassName()}.writeToBundle(destination, bundle)")
            bundleFunction.addStatement("return bundle")
            bundleFunction.endControlFlow()
        }

        bundleFunction.addStatement("throw io.sellmair.kompass.exception.MissingBundleSerializerException(destination)")


        val autoCraneExtensionFunction = FunSpec.builder("autoCrane")
                .addTypeVariable(TypeVariableName("T: Any"))
                .receiver(ClassName("io.sellmair.kompass", "KompassBuilder<T>"))
                .returns(ClassName("io.sellmair.kompass", "KompassBuilder<T>"))
                .addStatement("this.addCrane(AutoCrane())")
                .addStatement("return this")



        classBuilder.addFunction(bundleFunction.build())
        fileSpec.addType(classBuilder.build())
        fileSpec.addFunction(autoCraneExtensionFunction.build())

    }

}