package io.sellmair.kompass.compiler.destination.visitor

import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.TypeVariableName
import io.sellmair.kompass.compiler.common.ClassNames
import io.sellmair.kompass.compiler.destination.tree.DestinationsRenderTree

internal class KompassBuilderAutoMapVisitor : DestinationVisitor {
    override fun visit(target: DestinationsRenderTree) {
        target.kompassBuilderExtensions.autoMap.visit()
    }

    private fun FunSpec.Builder.visit() {
        buildHeader()
        buildImplementation()
    }

    private fun FunSpec.Builder.buildHeader() {
        addTypeVariable(TypeVariableName("T", ClassNames.any))
        receiver(ClassNames.kompassBuilder("T"))
        returns(ClassNames.kompassBuilder("T"))
    }

    private fun FunSpec.Builder.buildImplementation() {
        addStatement("this.addMap(AutoMap<T>())")
        addStatement("return this")
    }

}