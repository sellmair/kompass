package io.sellmair.kompass.compiler.common


interface Renderable<T> {
    fun render(target: T)
}

operator fun <T> Renderable<T>.plus(other: Renderable<T>): Renderable<T> = object : Renderable<T> {
    override fun render(target: T) {
        this@plus.render(target)
        other.render(target)
    }
}

