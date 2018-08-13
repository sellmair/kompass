package io.sellmair.kompass.compiler

import org.jetbrains.annotations.Nullable
import javax.lang.model.element.Element

fun Element.isNullable(): Boolean {
    return this.getAnnotation(Nullable::class.java) != null
}