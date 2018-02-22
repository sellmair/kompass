package io.sellmair.kompass.compiler.constructor

import io.sellmair.kompass.annotation.KompassConstructor
import io.sellmair.kompass.compiler.exception.DestinationConstructorException
import javax.lang.model.element.*
import javax.lang.model.util.ElementFilter

/**
 * Created by sebastiansellmair on 09.02.18.
 */
class LongestSuitableConstructorSelector : ConstructorSelector {
    override fun getKompassConstructor(element: Element): ExecutableElement {
        val constructors = ElementFilter.constructorsIn(element.enclosedElements)
            .filter { it.modifiers.contains(Modifier.PUBLIC) }
            .asSequence()
            .toList()

        if (constructors.isEmpty()) throw DestinationConstructorException("" +
            "${element.simpleName}: No public constructor found")

        constructors.getAnnotatedConstructor(element)?.let { return it }
        constructors.getLongestSuitableConstructor(element).let { return it }

    }


    private fun List<ExecutableElement>.getAnnotatedConstructor(base: Element): ExecutableElement? {
        val annotatedConstructors = this
            .filter { it.getAnnotation(KompassConstructor::class.java) != null }
            .toList()

        return when {
            annotatedConstructors.isEmpty() -> null
            annotatedConstructors.size == 1 -> annotatedConstructors.first()
            annotatedConstructors.size > 1 -> throw DestinationConstructorException("" +
                "${base.simpleName} has more than one constructors annotated with " +
                "'KompassConstructor'.")
            else -> null
        }
    }


    private fun List<ExecutableElement>.getLongestSuitableConstructor(base: Element)
        : ExecutableElement {

        val checkedConstructors = this
            .sortedByDescending { it.parameters.size }
            .map { it to it.isSuitable(base) }


        val suitableConstructors = checkedConstructors
            .filter { it.second == null }
            .toList();

        if (suitableConstructors.isNotEmpty()) return suitableConstructors.first().first

        checkedConstructors
            .mapNotNull { it.second }
            .firstOrNull()?.let { throw it }

        throw IllegalStateException("No suitable constructor found. No error message available. " +
            "This is a bug, for sure!")

    }

    private fun ExecutableElement.isSuitable(base: Element): Throwable? {
        return this.parameters.asSequence()
            .map { it.hasAccessor(base) }
            .firstOrNull()
    }

    private fun VariableElement.hasAccessor(base: Element): Throwable? {
        val containsFieldAccessor = ElementFilter
            .fieldsIn(base.enclosedElements)
            .asSequence()
            .filter { it.modifiers.contains(Modifier.PUBLIC) }
            .filter { it.simpleName == this.simpleName }
            .firstOrNull() != null

        val containsMethodAccessor = ElementFilter
            .methodsIn(base.enclosedElements)
            .asSequence()
            .filter { it.modifiers.contains(Modifier.PUBLIC) }
            .filter { it.returnType.toString() == this.asType().toString() }
            .filter { it.parameters.isEmpty() }
            .filter {
                it.simpleName == this.simpleName
                    || it.simpleName.toString() ==
                    "get${this.simpleName.toString().capitalize()}"
            }
            .firstOrNull() != null

        return if (!containsFieldAccessor && !containsMethodAccessor) {
            getNoAccessorThrowable(base as TypeElement, this)
        } else null
    }

    private fun getNoAccessorThrowable(baseElement: TypeElement, attribute: VariableElement)
        : Throwable {
        val message = "No accessor found for ${attribute.simpleName}:${attribute.asType()}\n" +
            "Fields: ${ElementFilter.fieldsIn(baseElement.enclosedElements)
                .map {
                    "${it.modifiers.joinToString(" ")} " +
                        "${it.simpleName}"
                }} \n" +
            "Methods: ${ElementFilter.methodsIn(baseElement.enclosedElements)
                .map {
                    "${it.modifiers.joinToString(" ")} " +
                        "${it.simpleName}"
                }}"
        throw Throwable(message)
    }


}