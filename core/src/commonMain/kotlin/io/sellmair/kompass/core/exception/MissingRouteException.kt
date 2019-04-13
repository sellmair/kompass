package io.sellmair.kompass.core.exception

import io.sellmair.kompass.core.KompassException

class MissingRouteException : KompassException {
    constructor() : super()
    constructor(message: String?) : super(message)
    constructor(message: String?, cause: Throwable?) : super(message, cause)
    constructor(cause: Throwable?) : super(cause)
}