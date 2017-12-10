package io.sellmair.kompass.compiler.util

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.asTypeName
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

