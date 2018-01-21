package io.sellmair.kompass.compiler.attribute.list

import com.squareup.javapoet.MethodSpec
import javax.annotation.processing.ProcessingEnvironment
import javax.lang.model.element.TypeElement
import javax.lang.model.element.VariableElement

/**
 * Created by sebastiansellmair on 21.01.18.
 */
class StringListAttributeSerializeLogic : ListAttributeSerializeLogic() {
    override fun addAttributeSerializeLogic(environment: ProcessingEnvironment,
                                            builder: MethodSpec.Builder,
                                            baseElement: TypeElement,
                                            attribute: VariableElement): Boolean {
        val stringList = getListType(environment, environment.elementUtils.getTypeElement("java.lang.String").asType())
        if (!environment.typeUtils.isAssignable(attribute.asType(), stringList)) return false
        val accessor = createAccessor(environment, builder, baseElement, attribute)
        val accessorArrayList = accessor + "ArrayList"

        builder.addStatement("" +
                "java.util.ArrayList<String> $accessorArrayList = io.sellmair.kompass.util.TypeUtil.toArrayList($accessor)")

        createBundlePut(builder, attribute, "putStringArrayList", accessorArrayList)
        return true
    }

    override fun addBundleAccessorLogic(environment: ProcessingEnvironment,
                                        builder: MethodSpec.Builder,
                                        baseElement: TypeElement,
                                        attribute: VariableElement,
                                        valueName: String): Boolean {
        val stringList = getListType(environment, environment.elementUtils.getTypeElement("java.lang.String").asType())
        if (!environment.typeUtils.isAssignable(attribute.asType(), stringList)) return false

        createBundleGet(builder, attribute, "getStringArrayList", valueName)
        return true
    }

}