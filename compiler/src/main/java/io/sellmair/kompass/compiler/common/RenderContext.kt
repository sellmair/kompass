package io.sellmair.kompass.compiler.common

import javax.annotation.processing.ProcessingEnvironment
import javax.lang.model.element.Element

/*
################################################################################################
PUBLIC API
################################################################################################
*/

interface RenderContext : ProcessingEnvironment {
    val elements: List<Element>

    companion object
}

fun RenderContext.Companion.wrap(
    elements: Iterable<Element>, environment: ProcessingEnvironment): RenderContext {
    return RenderContextWrapper(elements.toList(), environment)
}

/*
################################################################################################
PRIVATE IMPLEMENTATION
################################################################################################
*/

private class RenderContextWrapper(
    override val elements: List<Element>,
    environment: ProcessingEnvironment) :
    RenderContext,
    ProcessingEnvironment by environment