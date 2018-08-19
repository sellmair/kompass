package io.sellmair.kompass.compiler.detour.visitor

import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.TypeVariableName
import com.squareup.kotlinpoet.asClassName
import io.sellmair.kompass.compiler.common.ClassNames
import io.sellmair.kompass.compiler.common.RenderContext
import io.sellmair.kompass.compiler.detour.tree.DetoursRenderTree
import io.sellmair.kompass.compiler.extension.RenderContextUse

/*
################################################################################################
INTERNAL API
################################################################################################
*/

internal class KompassBuilderAutoDetourVisitor(override val context: RenderContext) :
    DetourVisitor, RenderContextUse {

    /*
    ################################################################################################
    API
    ################################################################################################
    */

    override fun visit(target: DetoursRenderTree) {
        target.kompassBuilderExtensions.autoDetour.visit(target)
    }


    /*
    ################################################################################################
    PRIVATE
    ################################################################################################
    */

    private fun FunSpec.Builder.visit(target: DetoursRenderTree) {
        buildHeader()
        buildImplementation(target)
    }

    private fun FunSpec.Builder.buildHeader() {
        this.addTypeVariable(TypeVariableName("T", ClassNames.any))
        this.receiver(ClassNames.kompassBuilder("T"))
        this.returns(ClassNames.kompassBuilder("T"))
    }

    private fun FunSpec.Builder.buildImplementation(tree: DetoursRenderTree) {
        for (detour in tree.detours) {
            addDetour(detour)
        }
    }

    private fun FunSpec.Builder.addDetour(tree: DetoursRenderTree.DetourRenderTree) {
        addStatement("addDetour(%T())", tree.element.asClassName())
        addStatement("return this")
    }
}