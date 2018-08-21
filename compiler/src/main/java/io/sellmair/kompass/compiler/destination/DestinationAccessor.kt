package io.sellmair.kompass.compiler.destination

import javax.lang.model.element.Element
import javax.lang.model.element.ExecutableElement
import javax.lang.model.type.TypeMirror

/*
################################################################################################
INTERNAL API
################################################################################################
*/

internal sealed class DestinationAccessor {


    class Field(override val name: String,
                override val element: Element) : DestinationAccessor()


    class Method(override val name: String,
                 override val element: ExecutableElement) : DestinationAccessor()


    abstract val name: String

    abstract val element: Element

    override fun toString(): String {
        return when (this) {
            is Field -> element.simpleName.toString()
            is Method -> element.simpleName.toString() + "()"
        }
    }

    val type: TypeMirror
        get() = when (this) {
            is Field -> element.asType()
            is Method -> element.returnType
        }
}