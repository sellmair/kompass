package io.sellmair.kompass.compiler.attribute

/**
 * Created by sebastiansellmair on 10.12.17.
 */
fun attributeSerializers(): List<AttributeSerializeLogic> = listOf(
        PrimitiveAttributeSerializeLogic(),
        ParcelableAttributeSerializeLogic(),
        StringAttributeSerializeLogic(),
        IntListAttributeSerializeLogic()
)