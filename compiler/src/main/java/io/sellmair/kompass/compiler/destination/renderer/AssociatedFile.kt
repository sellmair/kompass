package io.sellmair.kompass.compiler.destination.renderer

import com.squareup.kotlinpoet.FileSpec
import javax.lang.model.element.Element

/*
################################################################################################
PUBLIC API
################################################################################################
*/

interface AssociatedFile {
    val spec: FileSpec
    val elements: List<Element>

    fun associatedBy(element: Element): AssociatedFile
    fun associatedBy(elements: List<Element>): AssociatedFile
}

infix fun FileSpec.associatedBy(element: Element): AssociatedFile {
    return AssociatedFileImpl(this, listOf(element))
}

infix fun FileSpec.associatedBy(elements: List<Element>): AssociatedFile {
    return AssociatedFileImpl(this, elements)
}


/*
################################################################################################
PRIVATE IMPLEMENTATION
################################################################################################
*/

private class AssociatedFileImpl(override val spec: FileSpec,
                                 override val elements: List<Element>) : AssociatedFile {
    override fun associatedBy(element: Element): AssociatedFile {
        return AssociatedFileImpl(spec, elements + element)
    }

    override fun associatedBy(elements: List<Element>): AssociatedFile {
        return AssociatedFileImpl(spec, elements + elements)
    }

}