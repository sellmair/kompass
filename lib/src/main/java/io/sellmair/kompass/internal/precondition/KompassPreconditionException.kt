package io.sellmair.kompass.internal.precondition

internal class KompassPreconditionException : Exception {
    constructor() : super()
    constructor(message: String?) : super(message)
}