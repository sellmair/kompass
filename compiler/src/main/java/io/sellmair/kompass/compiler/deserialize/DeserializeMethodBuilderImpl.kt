package io.sellmair.kompass.compiler.deserialize

import com.squareup.javapoet.ClassName
import com.squareup.javapoet.MethodSpec
import com.squareup.javapoet.TypeName
import com.squareup.javapoet.TypeSpec
import io.sellmair.kompass.compiler.attribute.AttributeSerializeLogic
import io.sellmair.kompass.compiler.exception.UnsupportedTypeException
import io.sellmair.kompass.compiler.getKompassConstructor
import javax.annotation.processing.ProcessingEnvironment
import javax.lang.model.element.Modifier
import javax.lang.model.element.TypeElement

/**
 * Created by sebastiansellmair on 10.12.17.
 */
internal class DeserializeMethodBuilderImpl(private val logicParts: List<AttributeSerializeLogic>)
    : DeserializeMethodBuilder {
    override fun addDeserializeMethod(environment: ProcessingEnvironment,
                                      builder: TypeSpec.Builder,
                                      element: TypeElement): TypeSpec.Builder {
        val kompassConstructor = element.getKompassConstructor()

        val methodBuilder = MethodSpec.methodBuilder(DeserializeMethodBuilder.METHOD_NAME)
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(TypeName.get(element.asType()))
                .addParameter(ClassName.get("android.os", "Bundle"),
                        DeserializeMethodBuilder.PARAM_BUNDLE)

        kompassConstructor.parameters
                .asSequence().withIndex()
                .forEach { (index, attribute) ->
                    val valueName = "component$index"
                    val handled = logicParts.asSequence()
                            .map {
                                it.addBundleAccessorLogic(environment,
                                        methodBuilder, element, attribute, valueName)
                            }
                            .filter { it }
                            .toList().isNotEmpty()

                    if (!handled) throw UnsupportedTypeException(attribute)
                }

        methodBuilder.addCode("\n")
        val constructorStatement = "return new ${element.asType()}(${
        kompassConstructor.parameters
                .asSequence()
                .withIndex()
                .map { "component${it.index}" }
                .joinToString(", ")
        })"
        methodBuilder.addStatement(constructorStatement)
        return builder.addMethod(methodBuilder.build())

    }

}