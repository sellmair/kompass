package io.sellmair.kompass.compiler.pilot

import com.squareup.kotlinpoet.*
import javax.annotation.processing.ProcessingEnvironment
import javax.lang.model.element.TypeElement

/**
 * Created by sebastiansellmair on 03.01.18.
 */
interface PilotBuilder {
    fun buildPilotType(environment: ProcessingEnvironment, fileSpec: FileSpec.Builder, elements: List<TypeElement>)
}

class PilotBuilderImpl : PilotBuilder {
    override fun buildPilotType(environment: ProcessingEnvironment, fileSpec: FileSpec.Builder, elements: List<TypeElement>) {
        val classBuilder = TypeSpec.classBuilder("AutoDetourPilot")
                .superclass(ClassName("io.sellmair.kompass", "OpenDetourPilot"))

        val initBlock = CodeBlock.builder()
        elements.asSequence()
                .forEach { element ->
                    fileSpec.addAliasedImport(element.asClassName(), element.asClassName().simpleName())
                    initBlock.addStatement("" +
                            "this.registerDetour(${element.asClassName().simpleName()}())"
                    )
                }

        classBuilder.addInitializerBlock(initBlock.build())
        fileSpec.addType(classBuilder.build())
    }

}