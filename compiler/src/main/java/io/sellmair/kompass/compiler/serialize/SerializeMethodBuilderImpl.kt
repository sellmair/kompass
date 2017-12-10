package io.sellmair.kompass.compiler.serialize

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
internal class SerializeMethodBuilderImpl(
        private val logicParts: List<AttributeSerializeLogic>
) : SerializeMethodBuilder {


    override fun addSerializeMethod(environment: ProcessingEnvironment,
                                    builder: TypeSpec.Builder,
                                    element: TypeElement): TypeSpec.Builder {

        val constructor = element.getKompassConstructor()
        val methodBuilder = MethodSpec.methodBuilder(SerializeMethodBuilder.METHOD_NAME)
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(TypeName.VOID)
                .addParameter(TypeName.get(element.asType()), SerializeMethodBuilder.PARAM_DESTINATION)
                .addParameter(ClassName.get("android.os", "Bundle"),
                        SerializeMethodBuilder.PARAM_BUNDLE)

        constructor.parameters.asSequence()
                .forEachIndexed { index, attribute ->
                    if (index > 0) methodBuilder.addCode("\n\n")
                    val handled = logicParts.asSequence()
                            .map {
                                it.addAttributeSerializeLogic(environment, methodBuilder,
                                        element, attribute)
                            }
                            .filter { it }
                            .toList()
                            .isNotEmpty()

                    if (!handled) throw UnsupportedTypeException(attribute)
                }


        return builder.addMethod(methodBuilder.build())

    }


}

