package io.sellmair.kompass.compiler.destination.visitor


import com.squareup.kotlinpoet.*
import io.sellmair.kompass.compiler.common.ClassNames
import io.sellmair.kompass.compiler.common.RenderContext
import io.sellmair.kompass.compiler.destination.tree.DestinationRenderTree
import io.sellmair.kompass.compiler.destination.tree.DestinationsRenderTree
import io.sellmair.kompass.compiler.extension.RenderContextUse
import io.sellmair.kompass.compiler.extension.target
import javax.lang.model.type.TypeMirror

internal class AutoMapVisitor(override val context: RenderContext) : DestinationVisitor, RenderContextUse {
    override fun visit(target: DestinationsRenderTree) {
        target.autoMap.type.visit()
        target.autoMap.get.visit(target)
    }

    private fun TypeSpec.Builder.visit() {
        addTypeVariable(TypeVariableName("Destination", ClassNames.any))
        addSuperinterface(ClassNames.kompassMap("Destination"))
    }

    private fun FunSpec.Builder.visit(tree: DestinationsRenderTree) {
        buildHeader()
        buildImplementation(tree)
    }

    private fun FunSpec.Builder.buildHeader() {
        returns(ClassNames.kompassRoute.asNullable())
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
        val target = tree.element.target
        if (target != null) {
            destinationCase(tree, target)
        }
    }

    private fun FunSpec.Builder.destinationCase(
        tree: DestinationRenderTree,
        target: TypeMirror) {
        when {
            target.isAssignable(ClassNames.activity.asType()) ->
                activityDestinationCase(tree, target)

            target.isAssignable(ClassNames.fragment.asType()) ->
                fragmentDestinationCase(tree, target)

            target.isAssignable(ClassNames.view.asType()) ->
                viewDestinationCase(tree, target)
        }
    }

    private fun FunSpec.Builder.activityDestinationCase(
        tree: DestinationRenderTree,
        target: TypeMirror) {
        addStatement("""
        is %T -> %T::class.asRoute()
        """.trimIndent(),
            tree.element.asType().asTypeName(),
            target.asTypeName())
    }

    private fun FunSpec.Builder.fragmentDestinationCase(
        tree: DestinationRenderTree,
        target: TypeMirror) {
        addStatement("""
        is %T -> %T().asRoute()
        """.trimIndent(),
            tree.element.asType().asTypeName(),
            target.asTypeName())
    }

    private fun FunSpec.Builder.viewDestinationCase(
        tree: DestinationRenderTree,
        target: TypeMirror) {
        addStatement("""
        is %T -> %T::class.asRoute()
        """.trimIndent(),
            tree.element.asType().asTypeName(),
            target.asTypeName())
    }
}