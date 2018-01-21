package io.sellmair.kompass.compiler.attribute.array

import com.squareup.javapoet.MethodSpec
import javax.annotation.processing.ProcessingEnvironment
import javax.lang.model.element.TypeElement
import javax.lang.model.element.VariableElement

abstract class ObjectArrayAttributeSerializeLogic : ArrayAttributeSerializeLogic() {
    abstract val type: String
    open fun putMethodName(type: TypeElement): String = "put${type.simpleName.toString().capitalize()}Array"
    open fun getMethodName(type: TypeElement): String = "get${type.simpleName.toString().capitalize()}Array"

    override fun addAttributeSerializeLogic(environment: ProcessingEnvironment,
                                            builder: MethodSpec.Builder,
                                            baseElement: TypeElement,
                                            attribute: VariableElement): Boolean {
        val type = environment.elementUtils.getTypeElement(type)
        val arrayType = attribute.arrayType ?: return false
        if (!environment.typeUtils.isAssignable(arrayType.componentType, type.asType())) return false

        val accessor = createAccessor(environment, builder, baseElement, attribute)
        createBundlePut(builder, attribute, putMethodName(type), accessor)
        return true
    }

    override fun addBundleAccessorLogic(environment: ProcessingEnvironment,
                                        builder: MethodSpec.Builder,
                                        baseElement: TypeElement,
                                        attribute: VariableElement,
                                        valueName: String): Boolean {
        val type = environment.elementUtils.getTypeElement(type)

        val arrayType = attribute.arrayType ?: return false
        if (!environment.typeUtils.isAssignable(arrayType.componentType, type.asType())) return false
        createBundleGet(builder,
                attribute,
                getMethodName(type),
                valueName,
                castTo = attribute.asType().toString())
        return true
    }
}

class ParcelableArrayAttributeSerializeLogic : ObjectArrayAttributeSerializeLogic() {
    override val type = "android.os.Parcelable"
}

class StringArrayAttributeSerializeLogic : ObjectArrayAttributeSerializeLogic() {
    override val type = "java.lang.String"
}