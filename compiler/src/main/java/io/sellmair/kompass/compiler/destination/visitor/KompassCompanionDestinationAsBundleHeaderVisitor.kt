package io.sellmair.kompass.compiler.destination.visitor

import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.asClassName
import io.sellmair.kompass.compiler.ClassNames
import io.sellmair.kompass.compiler.destination.tree.DestinationRenderTree
import io.sellmair.kompass.compiler.destination.tree.DestinationsRenderTree

class KompassCompanionDestinationAsBundleHeaderVisitor : DestinationVisitor {
    override fun visit(target: DestinationsRenderTree) {
        for (destination in target.destinations) {
            destination.extensions.kompassCompanionExtensions.destinationAsBundle.visit(destination)
        }
    }

    private fun FunSpec.Builder.visit(destinationRenderTree: DestinationRenderTree) {
        receiver(ClassNames.kompassCompanion)
        returns(ClassNames.bundle)
        addParameter("destination", destinationRenderTree.element.asClassName())
    }
}

