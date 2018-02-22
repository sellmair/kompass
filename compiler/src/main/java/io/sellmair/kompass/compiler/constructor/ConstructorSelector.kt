package io.sellmair.kompass.compiler.constructor

import javax.lang.model.element.Element
import javax.lang.model.element.ExecutableElement

/**
 * Created by sebastiansellmair on 09.02.18.
 */
interface ConstructorSelector {
    fun getKompassConstructor(element: Element): ExecutableElement

    companion object {
        fun create(): ConstructorSelector {
            return LongestSuitableConstructorSelector()
        }
    }
}