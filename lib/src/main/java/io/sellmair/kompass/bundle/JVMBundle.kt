package io.sellmair.kompass.bundle

import android.os.Bundle
import android.os.Parcelable
import java.io.Serializable

/**
 * Created by sebastiansellmair on 09.12.17.
 */
class BundleAdapter(val bundle: Bundle = Bundle()) : JVMBundle {

    override fun put(key: String, obj: Any) {
        when (obj) {
            is String -> bundle.putString(key, obj)
            is Bundle -> bundle.putBundle(key, obj)
            is Byte -> bundle.putByte(key, obj)
            is ByteArray -> bundle.putByteArray(key, obj)
            is Char -> bundle.putChar(key, obj)
            is CharArray -> bundle.putCharArray(key, obj)
            is CharSequence -> bundle.putCharSequence(key, obj)
            is Float -> bundle.putFloat(key, obj)
            is FloatArray -> bundle.putFloatArray(key, obj)
            is Parcelable -> bundle.putParcelable(key, obj)
            is Serializable -> bundle.putSerializable(key, obj)
            is Short -> bundle.putShort(key, obj)
            is ShortArray -> bundle.putShortArray(key, obj)
            else -> throw Throwable("Unsupported type ${obj.javaClass.simpleName}")
        }

    }


    override fun <T : Any> put(key: String, clazz: Class<T>, list: List<T>) {
        when {
            Int::class.java.isAssignableFrom(clazz) -> bundle
                    .putIntegerArrayList(key, ArrayList(list.map { it as Int }))

            Parcelable::class.java.isAssignableFrom(clazz) -> bundle
                    .putParcelableArrayList(key, ArrayList(list.map { it as Parcelable }))

            else -> TODO("Unsupported type")
        }
    }

    override fun <T : Any> put(key: String, clazz: Class<T>, array: Array<T>) {
        TODO("not implemented")
    }


    override fun get(key: String): Any? {
        return bundle.get(key)
    }


}