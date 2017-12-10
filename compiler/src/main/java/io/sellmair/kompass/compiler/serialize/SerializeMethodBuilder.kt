package io.sellmair.kompass.compiler.serialize

import com.squareup.javapoet.TypeSpec
import javax.annotation.processing.ProcessingEnvironment
import javax.lang.model.element.TypeElement

/**
 * Created by sebastiansellmair on 10.12.17.
 */
internal interface SerializeMethodBuilder {
    fun addSerializeMethod(environment: ProcessingEnvironment,
                           builder: TypeSpec.Builder,
                           element: TypeElement): TypeSpec.Builder


    companion object {
        const val METHOD_NAME = "writeToBundle"
        const val PARAM_DESTINATION = "destination"
        const val PARAM_BUNDLE = "bundle"
    }
}


