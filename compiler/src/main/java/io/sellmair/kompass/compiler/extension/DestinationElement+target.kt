package io.sellmair.kompass.compiler.extension

import io.sellmair.kompass.annotation.Destination
import io.sellmair.kompass.compiler.destination.DestinationElement
import javax.lang.model.type.MirroredTypesException
import javax.lang.model.type.TypeMirror

internal val DestinationElement.target: TypeMirror?
    get() {
        try {
            this.getAnnotation(Destination::class.java).target
        } catch (e: MirroredTypesException) {
            return e.typeMirrors.firstOrNull()
        }

        return null

    }