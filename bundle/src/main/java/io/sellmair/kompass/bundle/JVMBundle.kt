package io.sellmair.kompass.bundle

interface JVMBundle {
    fun <T : Any> put(key: String, clazz: Class<T>, list: List<T>)
    fun <T : Any> put(key: String, clazz: Class<T>, array: Array<T>)
    fun put(key: String, obj: Any)
    fun get(key: String): Any?
}
