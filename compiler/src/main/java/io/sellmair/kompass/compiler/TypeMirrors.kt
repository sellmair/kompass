package io.sellmair.kompass.compiler

import com.squareup.kotlinpoet.ClassName
import io.sellmair.kompass.compiler.destination.RenderContext
import io.sellmair.kompass.compiler.destination.RenderContextSensitive
import io.sellmair.kompass.compiler.extension.RenderContextUse
import io.sellmair.kompass.compiler.extension.invoke
import javax.lang.model.type.DeclaredType
import javax.lang.model.type.TypeMirror

/*
################################################################################################
INTERNAL API
################################################################################################
*/

internal val RenderContextUse.types: CommonTypes
    get() = CommonTypesImpl(this.context)

interface CommonTypes : RenderContextSensitive {
    val list: TypeMirror
    val listOfBoolean: DeclaredType
    val listOfChar: DeclaredType
    val listOfByte: DeclaredType
    val listOfShort: DeclaredType
    val listOfInt: DeclaredType
    val listOfLong: DeclaredType
    val listOfFloat: DeclaredType
    val listOfDouble: DeclaredType
    val listOfString: DeclaredType
    val listOutParcelable: DeclaredType
    fun listOut(className: ClassName): DeclaredType
}

private class CommonTypesImpl(override val context: RenderContext) : CommonTypes {
    override val list: TypeMirror get() = context { ClassNames.list.asType() }

    override val listOfBoolean: DeclaredType
        get() = context { ClassNames.list.asDeclaredType(ClassNames.boolean) }

    override val listOfChar: DeclaredType
        get() = context { ClassNames.list.asDeclaredType(ClassNames.char) }

    override val listOfByte: DeclaredType
        get() = context { ClassNames.list.asDeclaredType(ClassNames.byte) }

    override val listOfShort: DeclaredType
        get() = context { ClassNames.list.asDeclaredType(ClassNames.short) }

    override val listOfInt: DeclaredType
        get() = context { ClassNames.list.asDeclaredType(ClassNames.integer) }

    override val listOfLong: DeclaredType
        get() = context { ClassNames.list.asDeclaredType(ClassNames.long) }

    override val listOfFloat: DeclaredType
        get() = context { ClassNames.list.asDeclaredType(ClassNames.float) }

    override val listOfDouble: DeclaredType
        get() = context { ClassNames.list.asDeclaredType(ClassNames.double) }

    override val listOfString: DeclaredType
        get() = context { ClassNames.list.asDeclaredType(ClassNames.string) }

    override val listOutParcelable: DeclaredType
        get() = listOut(ClassNames.parcelable)

    override fun listOut(className: ClassName): DeclaredType = context {
        val type = className.asType().asOutWildCard()
        ClassNames.list.asDeclaredType(type)
    }

}