package io.sellmair.kompass.compiler.builder

import com.squareup.kotlinpoet.FileSpec
import javax.annotation.processing.ProcessingEnvironment

/**
 * Created by sebastiansellmair on 03.01.18.
 */
interface BuilderExtensionsBuilder {
    fun buildExtensions(environment: ProcessingEnvironment, builder: FileSpec.Builder)
}

class BuilderExtensionsBuilderImpl : BuilderExtensionsBuilder {
    override fun buildExtensions(environment: ProcessingEnvironment, builder: FileSpec.Builder) {
        TODO("not implemented")
    }

}