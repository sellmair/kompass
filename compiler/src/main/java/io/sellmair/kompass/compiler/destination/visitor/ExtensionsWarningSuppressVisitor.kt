package io.sellmair.kompass.compiler.destination.visitor

import com.squareup.kotlinpoet.AnnotationSpec
import io.sellmair.kompass.compiler.destination.tree.DestinationRenderTree
import io.sellmair.kompass.compiler.destination.tree.DestinationsRenderTree


internal class ExtensionsWarningSuppressVisitor : DestinationVisitor {
    override fun visit(target: DestinationsRenderTree) {
        for (destination in target.destinations) {
            visit(destination)
        }
    }


    private fun visit(target: DestinationRenderTree) {
        val file = target.extensions.file
        file.addAnnotation(AnnotationSpec.builder(Suppress::class)
            .addMember("""
            "UNUSED_PARAMETER"
            """.trimIndent())
            .build())
    }
}
