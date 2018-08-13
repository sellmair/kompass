package io.sellmair.kompass.compiler.destination.visitor

import com.squareup.kotlinpoet.FunSpec
import io.sellmair.kompass.compiler.destination.tree.DestinationRenderTree
import io.sellmair.kompass.compiler.destination.tree.DestinationsRenderTree
import javax.lang.model.element.VariableElement

class KompassCompanionDestinationAsBundleImplementationDestinationVisitor : DestinationVisitor {
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
        addStatement("val bundle = Bundle()")
        for (parameter in tree.constructor.parameters) {
            visit(tree, parameter)
        }
        addStatement("return bundle")
    }

    private fun FunSpec.Builder.visit(tree: DestinationRenderTree, parameter: VariableElement) {
        addStatement("""
            """.trimIndent())
    }
}