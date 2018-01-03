package io.sellmair.kompass.compiler.util

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.asTypeName
import javax.lang.model.element.Element
import javax.lang.model.type.MirroredTypesException
import javax.lang.model.type.TypeMirror

/**
 * Created by sebastiansellmair on 09.12.17.
 */
fun TypeMirror.kotlinTypeName(): TypeName {
    return this.asTypeName().let { name ->
        when {
            name.toString() == "java.lang.String" -> ClassName("kotlin", "String")
            else -> name
        }
    }
}


/**
 * Thanks to
 * https://stackoverflow.com/questions/7687829/java-6-annotation-processing-getting-a-class-from-an-annotation
 */
val Element.destinationTarget: TypeMirror?
    get() {
        try {
            this.getAnnotation(io.sellmair.kompass.annotation.Destination::class.java).target
        } catch (e: MirroredTypesException) {
            return e.typeMirrors.firstOrNull()
        }

        return null
    }

