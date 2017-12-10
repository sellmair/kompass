package io.sellmair.kompass.compiler.serialize

import io.sellmair.kompass.compiler.attribute.attributeSerializers

internal fun SerializeMethodBuilder(): SerializeMethodBuilder {
    return SerializeMethodBuilderImpl(attributeSerializers())
}