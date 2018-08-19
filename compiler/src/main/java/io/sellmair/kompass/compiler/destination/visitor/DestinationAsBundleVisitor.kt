package io.sellmair.kompass.compiler.destination.visitor

import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.asClassName
import io.sellmair.kompass.compiler.ClassNames
import io.sellmair.kompass.compiler.destination.tree.DestinationRenderTree
import io.sellmair.kompass.compiler.destination.tree.DestinationsRenderTree

class DestinationAsBundleVisitor : DestinationVisitor {
    override fun visit(target: DestinationsRenderTree) {
        for (destination in target.destinations) {
            destination.extensions.destinationExtensions.asBundle.visit(destination)
        }
    }

    private fun FunSpec.Builder.visit(tree: DestinationRenderTree) {
        receiver(tree.element.asClassName())
        returns(ClassNames.bundle)

        val kompassFun = tree.extensions.kompassCompanionExtensions.destinationAsBundle.build()

        addCode(
            """
            return Kompass.${kompassFun.name}(this)

            """.trimIndent(), tree.extensions.kompassCompanionExtensions.destinationAsBundle.build())
    }

}