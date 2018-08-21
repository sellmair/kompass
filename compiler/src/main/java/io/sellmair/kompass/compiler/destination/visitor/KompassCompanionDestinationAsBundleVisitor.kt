package io.sellmair.kompass.compiler.destination.visitor

import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.asClassName
import io.sellmair.kompass.compiler.common.ClassNames
import io.sellmair.kompass.compiler.common.KompassUnsupportedDestinationTypeException
import io.sellmair.kompass.compiler.common.RenderContext
import io.sellmair.kompass.compiler.common.types
import io.sellmair.kompass.compiler.destination.DestinationAccessor
import io.sellmair.kompass.compiler.destination.tree.DestinationRenderTree
import io.sellmair.kompass.compiler.destination.tree.DestinationsRenderTree
import io.sellmair.kompass.compiler.extension.RenderContextUse
import io.sellmair.kompass.compiler.extension.isOptional
import javax.lang.model.type.ArrayType
import javax.lang.model.type.TypeKind

internal class KompassCompanionDestinationAsBundleVisitor(override val context: RenderContext) :
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

        for (accessor in tree.element.accessors) {
            writeToBundle(tree, accessor)
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
        tree: DestinationRenderTree, accessor: DestinationAccessor) {
        when {
            accessor.element.isOptional() -> writeOptionalToBundle(tree, accessor)
            else -> writeStraightToBundle(tree, accessor)
        }
    }

    private fun FunSpec.Builder.writeOptionalToBundle(
        tree: DestinationRenderTree, accessor: DestinationAccessor) {
        val putFunction = createBundlePutFunction(accessor)
        addCode("""

        val ${accessor.name} = destination.$accessor
        if(${accessor.name} != null) {
            bundle.$putFunction("${accessor.name}", ${accessor.name})
        }


        """.trimIndent())
    }

    private fun FunSpec.Builder.writeStraightToBundle(
        tree: DestinationRenderTree, accessor: DestinationAccessor) {
        val putFunction = createBundlePutFunction(accessor)

        addCode("""

        bundle.$putFunction("${accessor.name}", destination.$accessor)


        """.trimIndent())
    }

    private fun createBundlePutFunction(accessor: DestinationAccessor): String {
        val type = accessor.type

        return when {
            type.kind == TypeKind.ARRAY -> createBundlePutFunctionForArray(accessor)

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


    private fun createBundlePutFunctionForArray(accessor: DestinationAccessor): String {
        val type = accessor.type as ArrayType
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