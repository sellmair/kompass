package io.sellmair.kompass.compiler.attribute

import com.squareup.javapoet.MethodSpec
import io.sellmair.kompass.compiler.deserialize.DeserializeMethodBuilder
import javax.annotation.processing.ProcessingEnvironment
import javax.lang.model.element.TypeElement
import javax.lang.model.element.VariableElement
import javax.lang.model.type.DeclaredType
import javax.lang.model.type.TypeMirror

/**
 * Created by sebastiansellmair on 10.12.17.
 */
abstract class ListAttributeSerializeLogic : AbstractAttributeSerializeLogic() {

    protected fun getListType(environment: ProcessingEnvironment, elementType: TypeMirror): DeclaredType {
        val listInterfaceType = environment.elementUtils.getTypeElement("java.util.List")
        return environment.typeUtils.getDeclaredType(listInterfaceType, elementType)
    }

    protected fun VariableElement.isListOfType(environment: ProcessingEnvironment, elementType: TypeMirror): Boolean {
        val listType = getListType(environment, elementType)
        return environment.typeUtils.isAssignable(this.asType(), listType)
    }


}

class IntListAttributeSerializeLogic : ListAttributeSerializeLogic() {
    override fun addAttributeSerializeLogic(environment: ProcessingEnvironment,
                                            builder: MethodSpec.Builder,
                                            baseElement: TypeElement,
                                            attribute: VariableElement): Boolean {
        if (!isIntList(environment, attribute)) return false
        val integerType = environment.elementUtils.getTypeElement("java.lang.Integer").asType()
        val listValueName = createAccessor(environment, builder, baseElement, attribute)
        val arrayValueName = "${listValueName}Array"

        val arrayConversionStatement = "int[] $arrayValueName " +
                "= io.sellmair.kompass.util.TypeUtil.toIntArray($listValueName)"
        builder.addStatement(arrayConversionStatement)

        createBundlePut(builder, attribute, "putIntArray", arrayValueName)

        return true
    }

    override fun addBundleAccessorLogic(environment: ProcessingEnvironment,
                                        builder: MethodSpec.Builder,
                                        baseElement: TypeElement,
                                        attribute: VariableElement,
                                        valueName: String): Boolean {
        if (!isIntList(environment, attribute)) return false
        val arrayValueName = "${attribute}Array"

        val getIntArrayStatement = "int[] $arrayValueName = " + DeserializeMethodBuilder.PARAM_BUNDLE +
                ".getIntArray(\"${attribute.bundleArgumentName}\")"
        builder.addStatement(getIntArrayStatement)

        val listConversionStatement = "java.util.List<Integer> $valueName = " +
                "io.sellmair.kompass.util.TypeUtil.toIntegerList($arrayValueName)"

        builder.addStatement(listConversionStatement)

        return true
    }

    private fun isIntList(environment: ProcessingEnvironment, attribute: VariableElement): Boolean {
        val intType = environment.elementUtils.getTypeElement("java.lang.Integer")
        return attribute.isListOfType(environment, intType.asType())
    }
}