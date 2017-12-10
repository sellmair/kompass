package io.sellmair.kompass.compiler.attribute

import com.squareup.javapoet.CodeBlock
import com.squareup.javapoet.MethodSpec
import io.sellmair.kompass.compiler.serialize.SerializeMethodBuilder
import javax.annotation.processing.ProcessingEnvironment
import javax.lang.model.element.Modifier
import javax.lang.model.element.TypeElement
import javax.lang.model.element.VariableElement
import javax.lang.model.util.ElementFilter

abstract class AbstractAttributeSerializeLogic : AttributeSerializeLogic {
    protected fun createBundlePut(builder: MethodSpec.Builder,
                                  attribute: VariableElement,
                                  putMethodName: String,
                                  valueName: String) {
        val statement = SerializeMethodBuilder.PARAM_BUNDLE +
                ".$putMethodName(\"${attribute.bundleArgumentName}\", $valueName)"
        builder.addStatement(statement)
    }

    protected fun createBundleGet(builder: MethodSpec.Builder,
                                  attribute: VariableElement,
                                  getMethodName: String,
                                  valueName: String) {

        val statement = "${attribute.asType()} $valueName = " + SerializeMethodBuilder.PARAM_BUNDLE +
                ".$getMethodName(\"${attribute.bundleArgumentName}\")"
        builder.addStatement(statement)
    }

    protected fun createAccessor(environment: ProcessingEnvironment,
                                 builder: MethodSpec.Builder,
                                 baseElement: TypeElement,
                                 attribute: VariableElement): String {
        builder.addComment("Serialization of ${attribute.simpleName}")

        val fieldAccessor = ElementFilter.fieldsIn(baseElement.enclosedElements)
                .asSequence()
                .filter { it.modifiers.contains(Modifier.PUBLIC) }
                .filter { it.simpleName == attribute.simpleName }
                .firstOrNull()

        val methodAccessor = ElementFilter.methodsIn(baseElement.enclosedElements)
                .asSequence()
                .filter { it.modifiers.contains(Modifier.PUBLIC) }
                .filter { it.returnType.toString() == attribute.asType().toString() }
                .filter { it.parameters.isEmpty() }
                .filter {
                    it.simpleName == attribute.simpleName
                            || it.simpleName.toString() ==
                            "get${attribute.simpleName.toString().capitalize()}"
                }
                .firstOrNull()


        if (fieldAccessor == null && methodAccessor == null)
            throwNoAccessorFound(baseElement, attribute)

        fieldAccessor ?: methodAccessor?.let {
            builder.addCode(CodeBlock.builder()
                    .add("${attribute.asType()} ${attribute.simpleName} = " +
                            "${SerializeMethodBuilder.PARAM_DESTINATION}.${it.simpleName}")
                    .build())

            if (it == methodAccessor) builder.addCode("()")
            builder.addCode(";\n")
        }

        return attribute.simpleName.toString()
    }


    private fun throwNoAccessorFound(baseElement: TypeElement, attribute: VariableElement) {
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


    protected val VariableElement.bundleArgumentName: String
        get() = "ARG_${this.simpleName}"
}