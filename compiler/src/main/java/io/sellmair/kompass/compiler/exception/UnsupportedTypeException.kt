package io.sellmair.kompass.compiler.exception

import javax.lang.model.element.VariableElement

/**
 * Created by sebastiansellmair on 10.12.17.
 */
class UnsupportedTypeException(attribute: VariableElement) : Exception(attribute.asType().toString())