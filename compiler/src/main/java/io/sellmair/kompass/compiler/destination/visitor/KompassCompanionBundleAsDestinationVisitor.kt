package io.sellmair.kompass.compiler.destination.visitor

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.asClassName
import com.squareup.kotlinpoet.asTypeName
import io.sellmair.kompass.compiler.common.ClassNames
import io.sellmair.kompass.compiler.common.KompassUnsupportedDestinationTypeException
import io.sellmair.kompass.compiler.common.RenderContext
import io.sellmair.kompass.compiler.common.types
import io.sellmair.kompass.compiler.destination.DestinationAccessor
import io.sellmair.kompass.compiler.destination.tree.DestinationRenderTree
import io.sellmair.kompass.compiler.destination.tree.DestinationsRenderTree
import io.sellmair.kompass.compiler.extension.RenderContextUse
import io.sellmair.kompass.compiler.extension.invoke
import io.sellmair.kompass.compiler.extension.isOptional
import javax.lang.model.type.ArrayType
import javax.lang.model.type.TypeKind


internal class KompassCompanionBundleAsDestinationVisitor(
    override val context: RenderContext) :
    DestinationVisitor,
    RenderContextUse {
    override fun visit(target: DestinationsRenderTree) {
        for (destination in target.destinations) {
            destination
                .extensions
                .kompassCompanionExtensions
                .bundleAsDestination
                .visit(destination)
        }
    }

    private fun FunSpec.Builder.visit(tree: DestinationRenderTree) {
        buildHeader(tree)
        buildImplementation(tree)
    }

    private fun FunSpec.Builder.buildHeader(tree: DestinationRenderTree) {
        receiver(ClassNames.kompassCompanion)
        returns(tree.element.asClassName())
        addParameter("bundle", ClassNames.bundle)
    }

    private fun FunSpec.Builder.buildImplementation(tree: DestinationRenderTree) {
        buildImplementationHeader(tree)

        for (accessor in tree.element.accessors) {
            buildImplementationParam(tree, accessor)
            if (tree.element.accessors.last() != accessor) {
                addCode(",")
            }
        }

        buildImplementationFooter()
    }

    private fun FunSpec.Builder.buildImplementationHeader(tree: DestinationRenderTree) {
        addCode("return ${tree.element.simpleName}(")
    }

    private fun FunSpec.Builder.buildImplementationFooter() {
        addCode(")")
    }

    private fun FunSpec.Builder.buildImplementationParam(tree: DestinationRenderTree,
                                                         accessor: DestinationAccessor) {


        val getFunction = createBundleGetFunction(accessor)
        val missingKeyAppendix = createMissingKeyAppendix(accessor)
        val castAppendix = createCastAppendixFor(tree, accessor)

        addStatement(
            """

            ${accessor.name} = (bundle.$getFunction("${accessor.name}") $missingKeyAppendix) $castAppendix
            """.trimMargin()
        )
    }


    private fun createMissingKeyAppendix(accessor: DestinationAccessor): String {
        return if (accessor.element.isOptional()) ""
        else """ ?: throw IllegalArgumentException("Missing key ${accessor.name}")"""
    }


    private fun createCastAppendixFor(tree: DestinationRenderTree,
                                      accessor: DestinationAccessor): String = context {

        val type = accessor.type
        if (!type.isAssignable(ClassNames.serializable.asType())) return@context ""
        if (type.isAssignable(ClassNames.parcelable.asType())) return@context ""
        if (type.kind != TypeKind.DECLARED) return@context ""

        val className = type.asTypeName() as ClassName
        if (className.packageName.startsWith("java")) return@context ""


        val isOptional = accessor.element.isOptional()
        tree.extensions.file.addImport(className.packageName, className.simpleName)

        """as${if (isOptional) "?" else ""} ${className.simpleName} """

    }

    private fun createBundleGetFunction(accessor: DestinationAccessor): String {
        val type = accessor.type
        return when {

            type.kind == TypeKind.ARRAY -> createBundleGetFunctionForArray(accessor)

            type.isSameType(ClassNames.boolean.asType()) -> "getBooleanOrNull"
            type.kind == TypeKind.BOOLEAN -> "getBooleanOrNull"

            type.isSameType(ClassNames.byte.asType()) -> "getByteOrNull"
            type.kind == TypeKind.BYTE -> "getByteOrNull"

            type.isSameType(ClassNames.char.asType()) -> "getCharOrNull"
            type.kind == TypeKind.CHAR -> "getCharOrNull"

            type.isSameType(ClassNames.short.asType()) -> "getShortOrNull"
            type.kind == TypeKind.SHORT -> "getShortOrNull"

            type.isSameType(ClassNames.integer.asType()) -> "getIntOrNull"
            type.kind == TypeKind.INT -> "getIntOrNull"

            type.isSameType(ClassNames.long.asType()) -> "getLongOrNull"
            type.kind == TypeKind.LONG -> "getLongOrNull"

            type.isSameType(ClassNames.float.asType()) -> "getFloatOrNull"
            type.kind == TypeKind.FLOAT -> "getFloatOrNull"

            type.isSameType(ClassNames.double.asType()) -> "getDoubleOrNull"
            type.kind == TypeKind.DOUBLE -> "getDoubleOrNull"

            type.isSameType(ClassNames.string.asType()) -> "getString"

            type.isAssignable(ClassNames.parcelable.asType()) -> "getParcelable"

            type.isAssignable(ClassNames.serializable.asType()) -> "getSerializable"

            type.isAssignable(types.listOfBoolean) -> "getBooleanList"

            type.isAssignable(types.listOfChar) -> "getCharList"

            type.isAssignable(types.listOfByte) -> "getByteList"

            type.isAssignable(types.listOfShort) -> "getShortList"

            type.isAssignable(types.listOfInt) -> "getIntList"

            type.isAssignable(types.listOfLong) -> "getLongList"

            type.isAssignable(types.listOfFloat) -> "getFloatList"

            type.isAssignable(types.listOfDouble) -> "getDoubleList"

            type.isAssignable(types.listOfString) -> "getStringList"

            type.isAssignable(types.listOutParcelable) -> "getParcelableList"

            else -> throw KompassUnsupportedDestinationTypeException(type)
        }


    }

    private fun createBundleGetFunctionForArray(accessor: DestinationAccessor): String {
        val type = accessor.type as ArrayType
        val componentType = type.componentType
        return when {
            componentType.kind == TypeKind.BOOLEAN -> "getBooleanArray"
            componentType.kind == TypeKind.CHAR -> "getCharArray"
            componentType.kind == TypeKind.BYTE -> "getByteArray"
            componentType.kind == TypeKind.SHORT -> "getShortArray"
            componentType.kind == TypeKind.INT -> "getIntArray"
            componentType.kind == TypeKind.LONG -> "getLongArray"
            componentType.kind == TypeKind.FLOAT -> "getFloatArray"
            componentType.kind == TypeKind.DOUBLE -> "getDoubleArray"

            componentType.isAssignable(ClassNames.string.asType()) ->
                "getStringArray"

            else -> throw KompassUnsupportedDestinationTypeException(type)
        }
    }

}

