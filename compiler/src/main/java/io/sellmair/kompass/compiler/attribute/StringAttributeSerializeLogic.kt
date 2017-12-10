package io.sellmair.kompass.compiler.attribute

import com.squareup.javapoet.MethodSpec
import javax.annotation.processing.ProcessingEnvironment
import javax.lang.model.element.TypeElement
import javax.lang.model.element.VariableElement

/**
 * Created by sebastiansellmair on 10.12.17.
 */
class StringAttributeSerializeLogic : AbstractAttributeSerializeLogic() {
    override fun addAttributeSerializeLogic(environment: ProcessingEnvironment,
                                            builder: MethodSpec.Builder,
                                            baseElement: TypeElement,
                                            attribute: VariableElement): Boolean {
        if (!isStringType(environment, attribute)) return false
        val valueName = createAccessor(environment, builder, baseElement, attribute)
        createBundlePut(builder, attribute, "putString", valueName)
        return true
    }

    override fun addBundleAccessorLogic(environment: ProcessingEnvironment,
                                        builder: MethodSpec.Builder,
                                        baseElement: TypeElement,
                                        attribute: VariableElement,
                                        valueName: String): Boolean {
        if (!isStringType(environment, attribute)) return false
        createBundleGet(builder, attribute, "getString", valueName)
        return true
    }

    private fun isStringType(environment: ProcessingEnvironment, attribute: VariableElement): Boolean {
        val stringElement = environment.elementUtils.getTypeElement("java.lang.String")
        return environment.typeUtils.isAssignable(attribute.asType(), stringElement.asType())
    }
}