package io.sellmair.kompass.compiler.extension

import com.squareup.kotlinpoet.ClassName
import io.sellmair.kompass.compiler.common.RenderContext
import io.sellmair.kompass.compiler.common.RenderContextSensitive
import javax.lang.model.element.Element
import javax.lang.model.element.TypeElement
import javax.lang.model.type.DeclaredType
import javax.lang.model.type.TypeMirror
import javax.lang.model.util.Elements
import javax.lang.model.util.Types

/*
################################################################################################
INTERNAL API
################################################################################################
*/

internal operator fun <T> RenderContext.invoke(block: RenderContextUse.() -> T): T {
    return RenderContextUseImpl(this).block()
}

@Suppress("unused", "HasPlatformType", "CanBeParameter", "MemberVisibilityCanBePrivate")
internal interface RenderContextUse : RenderContextSensitive {

    val typeUtils: Types get() = context.typeUtils
    val elementUtils: Elements get() = context.elementUtils


    fun TypeMirror.isSameType(other: TypeMirror, vararg typeArgs: TypeMirror): Boolean {
        return typeUtils.isSameType(this, other)
    }

    fun TypeMirror.isSameType(name: String, vararg typeArgs: String): Boolean {
        return isSameType(name.asDeclaredType(*typeArgs))
    }

    fun TypeMirror.isAssignable(other: TypeMirror): Boolean {
        return typeUtils.isAssignable(this, other)
    }

    fun TypeMirror.isAssignable(name: String, vararg typeArgs: String): Boolean {
        return this.isAssignable(name.asDeclaredType(*typeArgs))
    }

    fun TypeMirror.asOutWildCard(): TypeMirror {
        return typeUtils.getWildcardType(this, null)
    }

    fun TypeMirror.asInWildCard(): TypeMirror {
        return typeUtils.getWildcardType(null, this)
    }

    fun TypeElement.asDeclaredType(vararg typeArgs: String): DeclaredType {
        val genericTypes = typeArgs.map { string -> string.asType() }.toTypedArray()
        return typeUtils.getDeclaredType(this, *genericTypes)
    }

    fun TypeElement.asDeclaredType(vararg typeArgs: Element): DeclaredType {
        val typeArgTypes = typeArgs.map(Element::asType).toTypedArray()
        return typeUtils.getDeclaredType(this, *typeArgTypes)
    }

    fun TypeElement.asDeclaredType(vararg typeArgs: TypeMirror): DeclaredType {
        return typeUtils.getDeclaredType(this, *typeArgs)
    }

    fun TypeElement.asDeclaredType(vararg typeArgs: ClassName): DeclaredType {
        val typeArgTypes = typeArgs.map { it.asType() }.toTypedArray()
        return typeUtils.getDeclaredType(this, *typeArgTypes)
    }

    fun String.asDeclaredType(vararg typeArgs: String): DeclaredType {
        return this.asElement().asDeclaredType(*typeArgs)
    }

    fun String.asDeclaredType(vararg typeArgs: Element): DeclaredType {
        return this.asElement().asDeclaredType(*typeArgs)
    }

    fun String.asDeclaredType(vararg typeArgs: TypeMirror): DeclaredType {
        return typeUtils.getDeclaredType(this.asElement(), *typeArgs)
    }

    fun String.asDeclaredType(vararg typeArgs: ClassName): DeclaredType {
        val typeArgTypes = typeArgs.map { it.asType() }.toTypedArray()
        return typeUtils.getDeclaredType(this.asElement(), *typeArgTypes)
    }

    fun String.asElement(): TypeElement {
        return elementUtils.getTypeElement(this)
    }

    fun String.asType(): TypeMirror {
        return this.asElement().asType()
    }

    fun ClassName.asElement(): TypeElement {
        return this.canonicalName.asElement()
    }

    fun ClassName.asDeclaredType(vararg typeArgs: String): DeclaredType {
        return this.canonicalName.asDeclaredType(*typeArgs)
    }

    fun ClassName.asDeclaredType(vararg typeArgs: Element): DeclaredType {
        return this.canonicalName.asDeclaredType(*typeArgs)
    }

    fun ClassName.asDeclaredType(vararg typeArgs: TypeMirror): DeclaredType {
        return this.canonicalName.asDeclaredType(*typeArgs)
    }

    fun ClassName.asDeclaredType(vararg typeArgs: ClassName): DeclaredType {
        return this.canonicalName.asDeclaredType(*typeArgs)
    }

    fun ClassName.asType(): TypeMirror {
        return this.canonicalName.asType()
    }
}


/*
################################################################################################
PRIVATE IMPLEMENTATION
################################################################################################
*/

private class RenderContextUseImpl(override val context: RenderContext) : RenderContextUse
