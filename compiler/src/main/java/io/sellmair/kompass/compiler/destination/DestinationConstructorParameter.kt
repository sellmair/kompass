package io.sellmair.kompass.compiler.destination

import javax.lang.model.element.VariableElement

/*
################################################################################################
INTERNAL API
################################################################################################
*/

internal class DestinationConstructorParameter(private val source: VariableElement) :
    VariableElement by source {

    override fun equals(other: Any?): Boolean {
        if (other is DestinationConstructorParameter) {
            return this.source == other.source
        }

        return false
    }

    override fun hashCode(): Int {
        return 31 * source.hashCode() + 13
    }
}