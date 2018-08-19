package io.sellmair.kompass.compiler.destination.visitor

import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.asClassName
import io.sellmair.kompass.compiler.ClassNames
import io.sellmair.kompass.compiler.KompassUnsupportedDestinationTypeException
import io.sellmair.kompass.compiler.destination.RenderContext
import io.sellmair.kompass.compiler.destination.tree.DestinationRenderTree
import io.sellmair.kompass.compiler.destination.tree.DestinationsRenderTree
import io.sellmair.kompass.compiler.extension.RenderContextUse
import io.sellmair.kompass.compiler.extension.isOptional
import io.sellmair.kompass.compiler.types
import javax.lang.model.element.VariableElement
import javax.lang.model.type.ArrayType
import javax.lang.model.type.TypeKind

class KompassCompanionDestinationAsBundleVisitor(override val context: RenderContext) :
    DestinationVisitor,
    RenderContextUse {
    override fun visit(target: DestinationsRenderTree) {
        for (destination in target.destinations) {
            destination
                .extensions
                .kompassCompanionExtensions
                .destinationAsBundle
                .visit(destination)
        }
    }

    private fun FunSpec.Builder.visit(tree: DestinationRenderTree) {
        buildHeader(tree)
        buildImplementation(tree)
    }

    private fun FunSpec.Builder.buildHeader(tree: DestinationRenderTree) {
        receiver(ClassNames.kompassCompanion)
        returns(ClassNames.bundle)
        addParameter("destination", tree.element.asClassName())
    }

    private fun FunSpec.Builder.buildImplementation(tree: DestinationRenderTree) {
        createNewBundle()

        for (parameter in tree.constructor.parameters) {
            writeToBundle(tree, parameter)
        }

        returnBundle()
    }

    private fun FunSpec.Builder.createNewBundle() {
        addStatement("val bundle = Bundle()")
    }

    private fun FunSpec.Builder.returnBundle() {
        addStatement("return bundle")
    }

    private fun FunSpec.Builder.writeToBundle(
        tree: DestinationRenderTree, parameter: VariableElement) {
        when {
            parameter.isOptional() -> writeOptionalToBundle(tree, parameter)
            else -> writeStraightToBundle(tree, parameter)
        }
    }

    private fun FunSpec.Builder.writeOptionalToBundle(
        tree: DestinationRenderTree, parameter: VariableElement) {
        val name = parameter.simpleName
        val putFunction = createBundlePutFunction(tree, parameter)
        addCode("""

        val $name = destination.$name
        if($name != null) {
            bundle.$putFunction("$name", $name)
        }


        """.trimIndent())
    }

    private fun FunSpec.Builder.writeStraightToBundle(
        tree: DestinationRenderTree, parameter: VariableElement) {
        val name = parameter.simpleName
        val putFunction = createBundlePutFunction(tree, parameter)

        addCode("""

        bundle.$putFunction("$name", destination.$name)


        """.trimIndent())
    }

    private fun createBundlePutFunction(tree: DestinationRenderTree, parameter: VariableElement): String {
        val type = parameter.asType()

        return when {
            type.kind == TypeKind.ARRAY -> createBundlePutFunctionForArray(parameter)

            type.isSameType(ClassNames.boolean.asType()) -> "putBoolean"
            type.kind == TypeKind.BOOLEAN -> "putBoolean"

            type.isSameType(ClassNames.byte.asType()) -> "putByte"
            type.kind == TypeKind.BYTE -> "putByte"

            type.isSameType(ClassNames.char.asType()) -> "putChar"
            type.kind == TypeKind.CHAR -> "putChar"

            type.isSameType(ClassNames.short.asType()) -> "putShort"
            type.kind == TypeKind.SHORT -> "putShort"

            type.isSameType(ClassNames.integer.asType()) -> "putInt"
            type.kind == TypeKind.INT -> "putInt"

            type.isSameType(ClassNames.long.asType()) -> "putLong"
            type.kind == TypeKind.LONG -> "putLong"

            type.isSameType(ClassNames.float.asType()) -> "putFloat"
            type.kind == TypeKind.FLOAT -> "putFloat"

            type.isSameType(ClassNames.double.asType()) -> "putDouble"
            type.kind == TypeKind.DOUBLE -> "putDouble"

            type.isSameType(ClassNames.string.asType()) -> "putString"

            type.isAssignable(ClassNames.parcelable.asType()) -> "putParcelable"

            type.isAssignable(ClassNames.serializable.asType()) -> "putSerializable"

            type.isAssignable(types.listOfBoolean) -> "putBooleanList"

            type.isAssignable(types.listOfChar) -> "putCharList"

            type.isAssignable(types.listOfByte) -> "putByteList"

            type.isAssignable(types.listOfShort) -> "putShortList"

            type.isAssignable(types.listOfInt) -> "putIntList"

            type.isAssignable(types.listOfLong) -> "putLongList"

            type.isAssignable(types.listOfFloat) -> "putFloatList"

            type.isAssignable(types.listOfDouble) -> "putDoubleList"

            type.isAssignable(types.listOfString) -> "putStringList"

            type.isAssignable(types.listOutParcelable) -> "putParcelableList"

            else -> throw KompassUnsupportedDestinationTypeException(type)
        }
    }


    private fun createBundlePutFunctionForArray(parameter: VariableElement): String {
        val type = parameter.asType() as ArrayType
        val componentType = type.componentType
        return when {
            componentType.kind == TypeKind.BOOLEAN -> "putBooleanArray"
            componentType.kind == TypeKind.CHAR -> "putCharArray"
            componentType.kind == TypeKind.BYTE -> "putByteArray"
            componentType.kind == TypeKind.SHORT -> "putShortArray"
            componentType.kind == TypeKind.INT -> "putIntArray"
            componentType.kind == TypeKind.LONG -> "putLongArray"
            componentType.kind == TypeKind.FLOAT -> "putFloatArray"
            componentType.kind == TypeKind.DOUBLE -> "putDoubleArray"

            componentType.isAssignable(ClassNames.string.asType()) ->
                "putStringArray"

            else -> throw KompassUnsupportedDestinationTypeException(type)
        }
    }


}