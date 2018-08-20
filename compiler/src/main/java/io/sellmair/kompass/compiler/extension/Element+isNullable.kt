package io.sellmair.kompass.compiler.extension

import org.jetbrains.annotations.Nullable
import javax.lang.model.element.Element

fun Element.isOptional(): Boolean {
    return this.getAnnotation(Nullable::class.java) != null
}