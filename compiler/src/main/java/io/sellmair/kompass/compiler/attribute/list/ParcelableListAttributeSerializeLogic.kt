package io.sellmair.kompass.compiler.attribute.list

import com.squareup.javapoet.MethodSpec
import javax.annotation.processing.ProcessingEnvironment
import javax.lang.model.element.TypeElement
import javax.lang.model.element.VariableElement

/**
 * Created by sebastiansellmair on 27.01.18.
 */
class ParcelableListAttributeSerializeLogic : ListAttributeSerializeLogic() {
    override fun addAttributeSerializeLogic(environment: ProcessingEnvironment,
                                            builder: MethodSpec.Builder,
                                            baseElement: TypeElement,
                                            attribute: VariableElement): Boolean {

        val parcelableType = environment.elementUtils.getTypeElement("android.os.Parcelable").asType()
        val wildcardParcelableType = environment.typeUtils.getWildcardType(parcelableType, null)

        val wildcardParcelableList = getListType(
                environment,
                wildcardParcelableType
        )


        if (!environment.typeUtils.isAssignable(attribute.asType(), wildcardParcelableList)) return false
        val accessor = createAccessor(environment, builder, baseElement, attribute)
        val accessorArrayList = accessor + "ArrayList"

        builder.addStatement("" +
                "java.util.ArrayList<android.os.Parcelable> $accessorArrayList " +
                "= io.sellmair.kompass.util.TypeUtil.toParcelableArrayList($accessor)")

        createBundlePut(builder, attribute, "putParcelableArrayList", accessorArrayList)
        return true
    }

    override fun addBundleAccessorLogic(environment: ProcessingEnvironment,
                                        builder: MethodSpec.Builder,
                                        baseElement: TypeElement,
                                        attribute: VariableElement,
                                        valueName: String): Boolean {
        val parcelableType = environment.elementUtils.getTypeElement("android.os.Parcelable").asType()
        val wildcardParcelableType = environment.typeUtils.getWildcardType(parcelableType, null)

        val wildcardParcelableList = getListType(
                environment,
                wildcardParcelableType
        )
        if (!environment.typeUtils.isAssignable(attribute.asType(), wildcardParcelableList)) return false

        createBundleGet(builder, attribute, "getParcelableArrayList", valueName)
        return true
    }

}