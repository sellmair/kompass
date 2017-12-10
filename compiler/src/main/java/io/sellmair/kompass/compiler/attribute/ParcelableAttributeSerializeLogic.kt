package io.sellmair.kompass.compiler.attribute

import com.squareup.javapoet.MethodSpec
import javax.annotation.processing.ProcessingEnvironment
import javax.lang.model.element.TypeElement
import javax.lang.model.element.VariableElement

/**
 * Created by sebastiansellmair on 10.12.17.
 */
class ParcelableAttributeSerializeLogic : AbstractAttributeSerializeLogic() {
    override fun addAttributeSerializeLogic(environment: ProcessingEnvironment,
                                            builder: MethodSpec.Builder,
                                            baseElement: TypeElement,
                                            attribute: VariableElement): Boolean {
        if (!isParcelableType(environment, attribute)) return false
        val valueName = createAccessor(environment, builder, baseElement, attribute)
        createBundlePut(builder, attribute, "putParcelable", valueName)
        return true
    }

    override fun addBundleAccessorLogic(environment: ProcessingEnvironment,
                                        builder: MethodSpec.Builder,
                                        baseElement: TypeElement, attribute: VariableElement,
                                        valueName: String): Boolean {
        if (!isParcelableType(environment, attribute)) return false
        createBundleGet(builder, attribute, "getParcelable", valueName)
        return true
    }


    private fun isParcelableType(environment: ProcessingEnvironment,
                                 attribute: VariableElement): Boolean {
        val parcelableElement = environment.elementUtils.getTypeElement("android.os.Parcelable")
        return environment.typeUtils.isAssignable(attribute.asType(), parcelableElement.asType())
    }
}