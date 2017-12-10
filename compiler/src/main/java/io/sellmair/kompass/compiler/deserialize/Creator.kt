package io.sellmair.kompass.compiler.deserialize

import io.sellmair.kompass.compiler.attribute.attributeSerializers

/**
 * Created by sebastiansellmair on 10.12.17.
 */
internal fun DeserializeMethodBuilder(): DeserializeMethodBuilder {
    return DeserializeMethodBuilderImpl(attributeSerializers())
}