package io.sellmair.kompass.compiler.destination.visitor

import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.TypeVariableName
import io.sellmair.kompass.compiler.common.ClassNames
import io.sellmair.kompass.compiler.destination.tree.DestinationRenderTree
import io.sellmair.kompass.compiler.destination.tree.DestinationsRenderTree

class AutoCraneVisitor : DestinationVisitor {
    override fun visit(target: DestinationsRenderTree) {
        target.autoCrane.type.visit(target)
        target.autoCrane.get.visit(target)
    }

    private fun TypeSpec.Builder.visit(tree: DestinationsRenderTree) {
        this.addTypeVariable(TypeVariableName("Destination", ClassNames.any))
        this.addSuperinterface(ClassNames.kompassCrane("Destination"))
    }

    private fun FunSpec.Builder.visit(tree: DestinationsRenderTree) {
        buildHeader()
        buildImplementation(tree)
    }

    private fun FunSpec.Builder.buildHeader() {
        returns(ClassNames.bundle.asNullable())
        addParameter("destination", TypeVariableName("Destination"))
        addModifiers(KModifier.OPERATOR)
        addModifiers(KModifier.OVERRIDE)
    }

    private fun FunSpec.Builder.buildImplementation(tree: DestinationsRenderTree) {
        returnWhenDestination()
        for (destination in tree.destinations) {
            destinationCase(destination)
        }
        elseCase()
        endWhen()
    }

    private fun FunSpec.Builder.returnWhenDestination() {
        beginControlFlow("return when(destination)")
    }

    private fun FunSpec.Builder.endWhen() {
        endControlFlow()
    }

    private fun FunSpec.Builder.elseCase() {
        addStatement("else -> null")
    }


    private fun FunSpec.Builder.destinationCase(tree: DestinationRenderTree) {
        addStatement("""
        is %T -> destination.%N()
        """.trimIndent(),
            tree.element.asType(),
            tree.extensions.destinationExtensions.asBundle.build())
    }
}