package io.sellmair.kompass.compiler.attribute

import io.sellmair.kompass.compiler.attribute.array.*
import io.sellmair.kompass.compiler.attribute.list.*

/**
 * Created by sebastiansellmair on 10.12.17.
 */
fun attributeSerializers(): List<AttributeSerializeLogic> = listOf(
        PrimitiveAttributeSerializeLogic(),
        ParcelableAttributeSerializeLogic(),
        StringAttributeSerializeLogic(),
        IntListAttributeSerializeLogic(),
        FloatListAttributeSerializeLogic(),
        DoubleListAttributeSerializeLogic(),
        CharListAttributeSerializeLogic(),
        BooleanListAttributeSerializeLogic(),
        LongListAttributeSerializeLogic(),
        ShortListAttributeSerializeLogic(),
        IntArrayAttributeSerializeLogic(),
        ShortArrayAttributeSerializeLogic(),
        FloatArrayAttributeSerializeLogic(),
        DoubleArrayAttributeSerializeLogic(),
        LongArrayAttributeSerializeLogic(),
        BooleanArrayAttributeSerializeLogic(),
        CharArrayAttributeSerializeLogic(),
        ByteArrayAttributeSerializeLogic(),
        ParcelableArrayAttributeSerializeLogic(),
        StringArrayAttributeSerializeLogic(),
        StringListAttributeSerializeLogic()

)