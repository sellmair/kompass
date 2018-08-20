package io.sellmair.kompass.compiler.detour.renderer

import io.sellmair.kompass.compiler.common.FileRenderer
import io.sellmair.kompass.compiler.common.RenderContext
import io.sellmair.kompass.compiler.common.Renderable
import io.sellmair.kompass.compiler.common.associatedBy
import io.sellmair.kompass.compiler.detour.tree.DetoursRenderTree

/*
################################################################################################
INTERNAL API
################################################################################################
*/

internal class DetoursRenderTreeRenderer(
    private val context: RenderContext, private val fileRenderer: FileRenderer) :
    Renderable<DetoursRenderTree> {

    /*
    ################################################################################################
    API
    ################################################################################################
    */

    override fun render(target: DetoursRenderTree) {
        renderKompassBuilderExtensions(target)
    }

    /*
    ################################################################################################
    PRIVATE
    ################################################################################################
    */

    private fun renderKompassBuilderExtensions(target: DetoursRenderTree) {
        val fileSpec = target.kompassBuilderExtensions.file
            .addFunction(target.kompassBuilderExtensions.autoDetour.build())
            .build()

        val file = fileSpec.associatedBy(context.elements)
        fileRenderer.render(file)
    }

}