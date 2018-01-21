package io.sellmair.kompass.compiler.extension

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.asTypeName
import io.sellmair.kompass.compiler.serializerClassName
import javax.annotation.processing.ProcessingEnvironment
import javax.lang.model.element.TypeElement

/**
 * Created by sebastiansellmair on 10.12.17.
 */
interface ExtensionBuilder {
    fun buildSerializerFunctions(environment: ProcessingEnvironment, builder: FileSpec.Builder, element: TypeElement)
}


class ExtensionBuilderImpl : ExtensionBuilder {
    override fun buildSerializerFunctions(environment: ProcessingEnvironment, builder: FileSpec.Builder, element: TypeElement) {
        buildBundleExtension(environment, builder, element)
    }

    private fun buildBundleExtension(environment: ProcessingEnvironment, builder: FileSpec.Builder, element: TypeElement) {
        val serializerClass = ClassName(environment.elementUtils.getPackageOf(element).toString(), element.serializerClassName())
        builder.addFunction(FunSpec.builder("tryAs${element.simpleName}")
                .receiver(ClassName("android.os", "Bundle"))
                .returns(element.asType().asTypeName().asNullable())
                .beginControlFlow("try")
                .addStatement("return $serializerClass.createFromBundle(this)")
                .endControlFlow()
                .beginControlFlow("catch(e: Throwable)")
                .addStatement("return null")
                .endControlFlow()
                .build())


        builder.addFunction(FunSpec.builder("as${element.simpleName}")
                .receiver(ClassName("android.os", "Bundle"))
                .returns(element.asType().asTypeName())
                .beginControlFlow("try")
                .addStatement("return $serializerClass.createFromBundle(this)")
                .endControlFlow()
                .beginControlFlow("catch(e: Throwable)")
                .addStatement("throw IllegalArgumentException(e)")
                .endControlFlow()
                .build())
    }
}