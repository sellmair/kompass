package io.sellmair.kompass.compiler.attribute

import com.squareup.javapoet.MethodSpec
import javax.annotation.processing.ProcessingEnvironment
import javax.lang.model.element.TypeElement
import javax.lang.model.element.VariableElement

/**
 * Created by sebastiansellmair on 10.12.17.
 */
interface AttributeSerializeLogic {
    fun addAttributeSerializeLogic(environment: ProcessingEnvironment,
                                   builder: MethodSpec.Builder,
                                   baseElement: TypeElement,
                                   attribute: VariableElement): Boolean

    fun addBundleAccessorLogic(environment: ProcessingEnvironment,
                               builder: MethodSpec.Builder,
                               baseElement: TypeElement,
                               attribute: VariableElement,
                               valueName: String): Boolean
}