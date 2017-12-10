package io.sellmair.kompass.compiler.deserialize

import com.squareup.javapoet.TypeSpec
import javax.annotation.processing.ProcessingEnvironment
import javax.lang.model.element.TypeElement

/**
 * Created by sebastiansellmair on 10.12.17.
 */
internal interface DeserializeMethodBuilder {
    fun addDeserializeMethod(environment: ProcessingEnvironment,
                             builder: TypeSpec.Builder,
                             element: TypeElement): TypeSpec.Builder

    companion object {
        const val PARAM_BUNDLE = "bundle"
        const val METHOD_NAME = "createFromBundle"
    }
}