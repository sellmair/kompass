package io.sellmair.kompass.compiler.common

interface Visitor<T> {
    fun visit(target: T)
}

operator fun <T> Visitor<T>.plus(other: Visitor<T>): Visitor<T> = object : Visitor<T> {
    override fun visit(target: T) {
        this@plus.visit(target)
        other.visit(target)
    }

}