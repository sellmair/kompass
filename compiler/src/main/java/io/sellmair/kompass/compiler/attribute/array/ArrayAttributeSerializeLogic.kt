package io.sellmair.kompass.compiler.attribute.array

import io.sellmair.kompass.compiler.attribute.AbstractAttributeSerializeLogic
import javax.lang.model.element.VariableElement
import javax.lang.model.type.ArrayType
import javax.lang.model.type.TypeKind

/**
 * Created by sebastiansellmair on 21.01.18.
 */
abstract class ArrayAttributeSerializeLogic : AbstractAttributeSerializeLogic() {
    val VariableElement.arrayType: ArrayType?
        get() {
            if (this.asType().kind != TypeKind.ARRAY) return null
            return this.asType() as ArrayType
        }
}

