package io.sellmair.kompass.compiler

import io.sellmair.kompass.annotation.Destination
import io.sellmair.kompass.compiler.destination.DestinationElement
import io.sellmair.kompass.compiler.destination.RenderContext
import io.sellmair.kompass.compiler.destination.renderer.DestinationsTreeRenderer
import io.sellmair.kompass.compiler.destination.renderer.FileRenderer
import io.sellmair.kompass.compiler.destination.renderer.invoke
import io.sellmair.kompass.compiler.destination.tree.DestinationsRenderTree
import io.sellmair.kompass.compiler.destination.tree.from
import io.sellmair.kompass.compiler.destination.visitor.*
import io.sellmair.kompass.compiler.destination.wrap
import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.SourceVersion
import javax.lang.model.element.Element
import javax.lang.model.element.TypeElement

class DestinationProcessor : AbstractProcessor() {

    override fun getSupportedAnnotationTypes() = mutableSetOf(Destination::class.java.name)

    override fun getSupportedSourceVersion(): SourceVersion = SourceVersion.latest()

    override fun process(elements: MutableSet<out TypeElement>,
                         roundEnvironment: RoundEnvironment): Boolean {
        if (elements.isEmpty()) return false

        /*
        Find destination elements
         */
        val annotatedElements = roundEnvironment.getElementsAnnotatedWith(Destination::class.java)
        val destinationElements = destinationElements(annotatedElements)
        val context = RenderContext.wrap(destinationElements, processingEnv)

        /*
        Create the render tree
         */
        val renderTree = DestinationsRenderTree.from(context, destinationElements)


        /*
        Create visitors and visit the tree
         */
        val visitor = KompassCompanionDestinationAsBundleHeaderVisitor() +
            KompassCompanionBundleAsDestinationHeaderVisitor() +
            DestinationAsBundleHeaderDestinationVisitor() +
            BundleAsDestinationHeaderDestinationVisitor() +
            DestinationAsBundleImplementationDestinationVisitor() +
            BundleAsDestinationImplementationDestinationVisitor() +
            KompassCompanionDestinationAsBundleImplementationDestinationVisitor() +
            KompassCompanionBundleAsDestinationImplementationDestinationVisitor()

        visitor.visit(renderTree)

        /*
        Create the renderer and render the tree
         */
        val fileRenderer = FileRenderer(context)
        val treeRenderer = DestinationsTreeRenderer(context, fileRenderer)
        treeRenderer.render(renderTree)

        return true
    }


    private fun destinationElements(elements: Iterable<Element>): List<DestinationElement> {
        return elements.mapNotNull { it as? TypeElement }.map(::DestinationElement)
    }

}






