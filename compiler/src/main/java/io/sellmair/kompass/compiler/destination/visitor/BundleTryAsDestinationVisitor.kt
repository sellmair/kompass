package io.sellmair.kompass.compiler.destination.visitor

import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.asClassName
import io.sellmair.kompass.compiler.common.ClassNames
import io.sellmair.kompass.compiler.destination.tree.DestinationRenderTree
import io.sellmair.kompass.compiler.destination.tree.DestinationsRenderTree

internal class BundleTryAsDestinationVisitor : DestinationVisitor {
    override fun visit(target: DestinationsRenderTree) {
        for (destination in target.destinations) {
            destination.extensions.bundleExtensions.tryAsDestination.visit(destination)
        }
    }

    private fun FunSpec.Builder.visit(tree: DestinationRenderTree) {
        buildHeader(tree)
        buildImplementation(tree)
    }

    private fun FunSpec.Builder.buildHeader(tree: DestinationRenderTree) {
        returns(tree.element.asClassName().asNullable())
        receiver(ClassNames.bundle.asNullable())
    }

    private fun FunSpec.Builder.buildImplementation(tree: DestinationRenderTree) {
        addCode("""
        if(this == null) return null
        return try {
            this.%N()
        } catch (t: Throwable) {
            null
        }

        """.trimIndent(), tree.extensions.bundleExtensions.asDestination.build())
    }

}