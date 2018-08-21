@file:Suppress("MemberVisibilityCanBePrivate")

package io.sellmair.kompass.compiler.destination

import javax.lang.model.element.*

/*
################################################################################################
INTERNAL API
################################################################################################
*/

internal class DestinationElement(source: TypeElement) : TypeElement by source {

    /**
     * The actual constructor used to re-create this element from bundle
     */
    val constructor = DestinationConstructorElement.from(this)

    /**
     * All accessors to the bundle. Each accessor is suitable for one param
     * in the constructor.
     * Each param in the constructor is granted to have one accessor
     */
    val accessors: List<DestinationAccessor> = constructor.parameters.map(this::accessorFor)


    /**
     * Find an given accessor for a parameter
     */
    fun accessorFor(param: VariableElement): DestinationAccessor? {
        return this.fieldAccessorFor(param) ?: this.methodAccessorFor(param)
    }


    /**
     * Guaranteed accessor for type [DestinationConstructorParameter]
     */
    fun accessorFor(param: DestinationConstructorParameter): DestinationAccessor {
        return accessorFor(param as VariableElement) ?: throw IllegalArgumentException("" +
            "Strange DestinationConstructorParameter passed to 'accessorFor'. " +
            "No accessor was found!")
    }


    /*
    ################################################################################################
    PRIVATE
    ################################################################################################
    */

    /**
     * Finds a suitable Field Accessor if possible
     */
    private fun fieldAccessorFor(param: VariableElement): DestinationAccessor.Field? {
        fun Element.isField(): Boolean = this.kind == ElementKind.FIELD

        fun Element.nameMatches(): Boolean =
            this.simpleName.toString().toLowerCase() ==
                param.simpleName.toString().toLowerCase()

        return this.enclosedElements.asSequence()
            .filter(Element::isField)
            .filter(Element::nameMatches)
            .map { DestinationAccessor.Field(param.simpleName.toString(), it) }
            .firstOrNull()
    }

    /**
     * Finds a given method accessor if possible
     */
    private fun methodAccessorFor(param: VariableElement): DestinationAccessor.Method? {
        fun Element.isMethod(): Boolean = this.kind == ElementKind.METHOD

        fun Element.nameMatches(): Boolean {
            val paramName = param.simpleName.toString()
            val elementName = this.simpleName.toString()

            if (elementName.toLowerCase() == paramName.toLowerCase()) return true
            if (elementName.toLowerCase() == "get${paramName.toLowerCase()}") return true
            if (elementName.toLowerCase() == "create${paramName.toLowerCase()}") return true
            return false
        }

        fun Element.asExecutable(): ExecutableElement? = this as? ExecutableElement

        return this.enclosedElements.asSequence()
            .filter(Element::isMethod)
            .filter(Element::nameMatches)
            .mapNotNull(Element::asExecutable)
            .map { DestinationAccessor.Method(param.simpleName.toString(), it) }
            .firstOrNull()
    }

}

