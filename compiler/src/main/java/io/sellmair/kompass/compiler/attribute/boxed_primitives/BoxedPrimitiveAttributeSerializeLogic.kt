package io.sellmair.kompass.compiler.attribute.boxed_primitives

import com.squareup.javapoet.MethodSpec
import com.squareup.kotlinpoet.asTypeName
import io.sellmair.kompass.compiler.attribute.AbstractAttributeSerializeLogic
import io.sellmair.kompass.compiler.serialize.SerializeMethodBuilder
import javax.annotation.processing.ProcessingEnvironment
import javax.lang.model.element.TypeElement
import javax.lang.model.element.VariableElement

/**
 * Created by sebastiansellmair on 08.02.18.
 */
abstract class BoxedPrimitiveAttributeSerializeLogic : AbstractAttributeSerializeLogic() {
    abstract val className: String
    abstract val putMethodName: String
    abstract val getMethodName: String

    override fun addAttributeSerializeLogic(environment: ProcessingEnvironment,
                                            builder: MethodSpec.Builder,
                                            baseElement: TypeElement,
                                            attribute: VariableElement): Boolean {
        val boxedType = environment.elementUtils.getTypeElement(className)
        if (!environment.typeUtils.isSubtype(attribute.asType(), boxedType.asType())) {
            return false
        }

        val valueName = createAccessor(environment, builder, baseElement, attribute)

        builder.beginControlFlow("if($valueName!=null)")
        createBundlePut(builder, attribute, putMethodName, valueName)
        builder.endControlFlow()

        return true
    }

    override fun addBundleAccessorLogic(environment: ProcessingEnvironment,
                                        builder: MethodSpec.Builder,
                                        baseElement: TypeElement,
                                        attribute: VariableElement,
                                        valueName: String): Boolean {
        val boxedType = environment.elementUtils.getTypeElement(className)
        if (!environment.typeUtils.isSameType(attribute.asType(), boxedType.asType())) {
            return false
        }

        val initializeStatement = "${attribute.asType().asTypeName()} $valueName = null"

        val hasElementStatement = "" +
                "if(${SerializeMethodBuilder.PARAM_BUNDLE}" +
                ".containsKey(\"${attribute.bundleArgumentName}\"))"

        val overwriteStatement = "" +
                "$valueName = " +
                SerializeMethodBuilder.PARAM_BUNDLE +
                ".$getMethodName(\"${attribute.bundleArgumentName}\")"

        builder.addStatement(initializeStatement)
        builder.beginControlFlow(hasElementStatement)
        builder.addStatement(overwriteStatement)
        builder.endControlFlow()

        return true

    }
}

class BoxedIntegerAttributeSerializeLogic : BoxedPrimitiveAttributeSerializeLogic() {
    override val className: String
        get() = "java.lang.Integer"
    override val putMethodName: String
        get() = "putInt"
    override val getMethodName: String
        get() = "getInt"
}

class BoxedFloatAttributeSerializeLogic : BoxedPrimitiveAttributeSerializeLogic() {
    override val className: String
        get() = "java.lang.Float"
    override val putMethodName: String
        get() = "putFloat"
    override val getMethodName: String
        get() = "getFloat"
}

class BoxedDoubleAttributeSerializeLogic : BoxedPrimitiveAttributeSerializeLogic() {
    override val className: String
        get() = "java.lang.Double"
    override val putMethodName: String
        get() = "putDouble"
    override val getMethodName: String
        get() = "getDouble"

}

class BoxedBooleanAttributeSerializeLogic : BoxedPrimitiveAttributeSerializeLogic() {
    override val className: String
        get() = "java.lang.Boolean"
    override val putMethodName: String
        get() = "putBoolean"
    override val getMethodName: String
        get() = "getBoolean"
}

class BoxedShortAttributeSerializeLogic : BoxedPrimitiveAttributeSerializeLogic() {
    override val className: String
        get() = "java.lang.Short"
    override val putMethodName: String
        get() = "putShort"
    override val getMethodName: String
        get() = "getShort"

}