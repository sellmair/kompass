package io.sellmair.kompass.compiler.attribute

import com.squareup.javapoet.MethodSpec
import javax.annotation.processing.ProcessingEnvironment
import javax.lang.model.element.TypeElement
import javax.lang.model.element.VariableElement
import javax.lang.model.type.TypeKind

/**
 * Created by sebastiansellmair on 10.12.17.
 */
class PrimitiveAttributeSerializeLogic : AbstractAttributeSerializeLogic() {
    override fun addAttributeSerializeLogic(environment: ProcessingEnvironment,
                                            builder: MethodSpec.Builder,
                                            baseElement: TypeElement,
                                            attribute: VariableElement): Boolean {

        val putMethodName = when (attribute.asType().kind) {
            TypeKind.INT -> "putInt"
            TypeKind.LONG -> "putLong"
            TypeKind.DOUBLE -> "putDouble"
            TypeKind.FLOAT -> "putFloat"
            TypeKind.BOOLEAN -> "putBoolean"
            TypeKind.BYTE -> "putByte"
            TypeKind.SHORT -> "putShort"
            else -> null
        } ?: return false

        val valueName = createAccessor(environment, builder, baseElement, attribute)
        createBundlePut(builder, attribute, putMethodName, valueName)
        return true
    }


    override fun addBundleAccessorLogic(environment: ProcessingEnvironment,
                                        builder: MethodSpec.Builder,
                                        baseElement: TypeElement,
                                        attribute: VariableElement,
                                        valueName: String): Boolean {
        val getMethodName = when (attribute.asType().kind) {
            TypeKind.INT -> "getInt"
            TypeKind.LONG -> "getLong"
            TypeKind.DOUBLE -> "getDouble"
            TypeKind.FLOAT -> "getFloat"
            TypeKind.BOOLEAN -> "getBoolean"
            TypeKind.BYTE -> "getByte"
            TypeKind.SHORT -> "getShort"
            else -> null
        } ?: return false

        createBundleGet(builder, attribute, getMethodName, valueName)
        return true
    }


}


