package io.sellmair.kompass.internal.util


internal fun <P1, R> ((P1) -> R).curry(value: P1): () -> R {
    return { this(value) }
}

internal fun <P1, P2, R> ((P1, P2) -> R).curry(value: P1): (P2) -> R {
    return { second: P2 ->
        this(value, second)
    }
}