package io.sellmair.kompass.compiler.destination.visitor

import com.squareup.kotlinpoet.FunSpec
import io.sellmair.kompass.compiler.destination.tree.DestinationRenderTree
import io.sellmair.kompass.compiler.destination.tree.DestinationsRenderTree

class DestinationAsBundleImplementationDestinationVisitor : DestinationVisitor {
    override fun visit(target: DestinationsRenderTree) {
        for (destination in target.destinations) {
            destination.extensions.destinationExtensions.asBundle.visit(destination)
        }
    }

    private fun FunSpec.Builder.visit(tree: DestinationRenderTree) {
        val kompassFun = tree.extensions.kompassCompanionExtensions.destinationAsBundle.build()

        addCode(
            """
            return Kompass.${kompassFun.name}(this)

            """.trimIndent(), tree.extensions.kompassCompanionExtensions.destinationAsBundle.build())
    }

}