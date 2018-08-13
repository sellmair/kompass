package io.sellmair.kompass.compiler.destination.visitor

import com.squareup.kotlinpoet.FunSpec
import io.sellmair.kompass.compiler.destination.RenderContext
import io.sellmair.kompass.compiler.destination.tree.DestinationRenderTree
import io.sellmair.kompass.compiler.destination.tree.DestinationsRenderTree
import io.sellmair.kompass.compiler.isNullable
import javax.lang.model.element.Element
import javax.lang.model.element.TypeElement
import javax.lang.model.element.VariableElement
import javax.lang.model.type.TypeKind
import javax.lang.model.type.TypeMirror

class KompassCompanionBundleAsDestinationImplementationDestinationVisitor : DestinationVisitor {
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
        addCode("return ${tree.element.simpleName}(")
        for (parameter in tree.constructor.parameters) {
            visit(tree, parameter)
        }
        addCode(")")
    }

    private fun FunSpec.Builder.visit(tree: DestinationRenderTree, parameter: VariableElement) {
        val getFun = getFunFor(tree.context, parameter)
        val simpleName = parameter.simpleName
        val missingKeyAppendix = if (parameter.isNullable()) "" else """ ?: throw Exception("Missing key $simpleName")"""
        addStatement(
            """
                $simpleName = bundle.$getFun("$simpleName") $missingKeyAppendix
            """.trimIndent()
        )
    }


    private fun getFunFor(context: RenderContext, parameter: VariableElement): String {
        val type = parameter.asType()
        val typeUtils = context.typeUtils
        val elementUtils = context.elementUtils

        fun String.asElement(): TypeElement {
            return elementUtils.getTypeElement(this)
        }

        fun TypeMirror.isSameType(name: String): Boolean {
            return typeUtils.isSameType(this, name.asElement().asType())
        }

        fun TypeMirror.isAssignable(name: String, vararg generics: String): Boolean {
            val genericTypes = generics.map(String::asElement).map(Element::asType).toTypedArray()
            val probeType = typeUtils.getDeclaredType(name.asElement(), *genericTypes)
            return typeUtils.isAssignable(this, probeType)
        }

        return when {
            type.isSameType("java.lang.Integer") -> "getInt"
            type.kind == TypeKind.INT -> "getIntOrNull"

            type.isSameType("java.lang.Float") -> "getFloat"
            type.kind == TypeKind.FLOAT -> "getFloatOrNull"

            type.isSameType("java.lang.Double") -> "getDouble"
            type.kind == TypeKind.DOUBLE -> "getDouble"

            type.isSameType("java.lang.String") -> "getString"

            type.isAssignable("android.os.Parcelable") -> "getParcelable"

            type.isAssignable("java.io.Serializable") -> "getSerializable"

            type.isAssignable("java.util.List", "java.lang.String") -> "getStringList"

            type.isAssignable("java.util.List", "android.os.Parcelable") -> "getParcelableList"

            else -> throw Exception("Unsupported type $type")

        }
    }

}