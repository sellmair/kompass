package io.sellmair.kompass.compiler

import io.sellmair.kompass.annotation.Detour
import io.sellmair.kompass.compiler.common.FileRenderer
import io.sellmair.kompass.compiler.common.RenderContext
import io.sellmair.kompass.compiler.common.invoke
import io.sellmair.kompass.compiler.common.wrap
import io.sellmair.kompass.compiler.detour.DetourElement
import io.sellmair.kompass.compiler.detour.renderer.DetoursRenderTreeRenderer
import io.sellmair.kompass.compiler.detour.tree.DetoursRenderTree
import io.sellmair.kompass.compiler.detour.visitor.KompassBuilderAutoDetourVisitor
import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.SourceVersion
import javax.lang.model.element.Element
import javax.lang.model.element.TypeElement

class DetourProcessor : AbstractProcessor() {
    override fun getSupportedAnnotationTypes() = mutableSetOf(Detour::class.java.name)
    override fun getSupportedSourceVersion(): SourceVersion = SourceVersion.latest()

    override fun process(elements: MutableSet<out TypeElement>,
                         roundEnvironment: RoundEnvironment): Boolean {
        if (elements.isEmpty()) return false

        /*
        Find detour elements
         */
        val annotatedElements = roundEnvironment.getElementsAnnotatedWith(Detour::class.java)
        val detourElements = detourElements(annotatedElements)
        val context = RenderContext.wrap(detourElements, processingEnv)

        /*
        Create the render tree
         */
        val renderTree = DetoursRenderTree(context, detourElements)


        /*
        Visit the tree
         */
        val visitor = KompassBuilderAutoDetourVisitor(context)
        visitor.visit(renderTree)

        /*
        Render the tree
         */
        val renderer = DetoursRenderTreeRenderer(context, FileRenderer(context))
        renderer.render(renderTree)

        return true
    }


    private fun detourElements(elements: Iterable<Element>): List<DetourElement> {
        return elements.mapNotNull { it as? TypeElement }.map(::DetourElement)
    }
}