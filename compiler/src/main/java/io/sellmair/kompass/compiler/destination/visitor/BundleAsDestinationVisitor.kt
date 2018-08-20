package io.sellmair.kompass.compiler.destination.visitor

import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.asClassName
import io.sellmair.kompass.compiler.common.ClassNames
import io.sellmair.kompass.compiler.destination.tree.DestinationRenderTree
import io.sellmair.kompass.compiler.destination.tree.DestinationsRenderTree

class BundleAsDestinationVisitor : DestinationVisitor {
    override fun visit(target: DestinationsRenderTree) {
        for (destination in target.destinations) {
            destination.extensions.bundleExtensions.asDestination.visit(destination)
        }
    }

    private fun FunSpec.Builder.visit(tree: DestinationRenderTree) {
        returns(tree.element.asClassName())
        receiver(ClassNames.bundle.asNullable())

        val kompassCompanionFun = tree
            .extensions
            .kompassCompanionExtensions
            .bundleAsDestination.build()


        addCode("""
            if(this == null) throw IllegalArgumentException("Missing Bundle")
            return Kompass.${kompassCompanionFun.name}(this)

        """.trimIndent())
    }

}