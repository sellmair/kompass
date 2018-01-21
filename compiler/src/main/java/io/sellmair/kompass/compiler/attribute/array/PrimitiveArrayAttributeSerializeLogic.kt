package io.sellmair.kompass.compiler.attribute.array

import com.squareup.javapoet.MethodSpec
import javax.annotation.processing.ProcessingEnvironment
import javax.lang.model.element.TypeElement
import javax.lang.model.element.VariableElement
import javax.lang.model.type.TypeKind

abstract class PrimitiveArrayAttributeSerializeLogic : ArrayAttributeSerializeLogic() {
    abstract val primitive: String
    abstract val primitiveKind: TypeKind
    open val capitalizedPrimitive get() = primitive.capitalize()
    open val boxedPrimitive get() = capitalizedPrimitive

    override fun addAttributeSerializeLogic(environment: ProcessingEnvironment,
                                            builder: MethodSpec.Builder,
                                            baseElement: TypeElement,
                                            attribute: VariableElement): Boolean {
        val arrayType = attribute.arrayType ?: return false
        if (arrayType.componentType.kind != primitiveKind) return false

        val accessor = createAccessor(environment, builder, baseElement, attribute)
        createBundlePut(builder, attribute, "put${capitalizedPrimitive}Array", accessor)
        return true
    }

    override fun addBundleAccessorLogic(environment: ProcessingEnvironment,
                                        builder: MethodSpec.Builder,
                                        baseElement: TypeElement,
                                        attribute: VariableElement,
                                        valueName: String): Boolean {
        val arrayType = attribute.arrayType ?: return false
        if (arrayType.componentType.kind != primitiveKind) return false

        createBundleGet(builder, attribute, "get${capitalizedPrimitive}Array", valueName)
        return true
    }
}

class IntArrayAttributeSerializeLogic : PrimitiveArrayAttributeSerializeLogic() {
    override val primitiveKind = TypeKind.INT
    override val primitive = "int"
    override val boxedPrimitive = "Integer"
}

class FloatArrayAttributeSerializeLogic : PrimitiveArrayAttributeSerializeLogic() {
    override val primitiveKind = TypeKind.FLOAT
    override val primitive = "float"
}

class DoubleArrayAttributeSerializeLogic : PrimitiveArrayAttributeSerializeLogic() {
    override val primitiveKind = TypeKind.DOUBLE
    override val primitive = "double"
}


class ShortArrayAttributeSerializeLogic : PrimitiveArrayAttributeSerializeLogic() {
    override val primitiveKind = TypeKind.SHORT
    override val primitive = "short"
}

class LongArrayAttributeSerializeLogic : PrimitiveArrayAttributeSerializeLogic() {
    override val primitiveKind = TypeKind.LONG
    override val primitive = "long"
}

class ByteArrayAttributeSerializeLogic : PrimitiveArrayAttributeSerializeLogic() {
    override val primitiveKind = TypeKind.BYTE
    override val primitive = "byte"
}

class CharArrayAttributeSerializeLogic : PrimitiveArrayAttributeSerializeLogic() {
    override val primitiveKind = TypeKind.CHAR
    override val primitive = "char"
}

class BooleanArrayAttributeSerializeLogic : PrimitiveArrayAttributeSerializeLogic() {
    override val primitiveKind = TypeKind.BOOLEAN
    override val primitive = "boolean"
}