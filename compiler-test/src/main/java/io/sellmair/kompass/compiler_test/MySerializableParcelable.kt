package io.sellmair.kompass.compiler_test

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

class MySerializableParcelable() : Serializable, Parcelable {
    constructor(parcel: Parcel) : this() {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MySerializableParcelable> {
        override fun createFromParcel(parcel: Parcel): MySerializableParcelable {
            return MySerializableParcelable(parcel)
        }

        override fun newArray(size: Int): Array<MySerializableParcelable?> {
            return arrayOfNulls(size)
        }
    }
}