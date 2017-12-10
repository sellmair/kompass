package io.sellmair.kompass.app.model

import android.os.Parcel
import android.os.Parcelable

class SimpleParcelable(val id: Int, val child: SimpleParcelable?) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readParcelable(SimpleParcelable::class.java.classLoader)) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeParcelable(child, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SimpleParcelable> {
        override fun createFromParcel(parcel: Parcel): SimpleParcelable {
            return SimpleParcelable(parcel)
        }

        override fun newArray(size: Int): Array<SimpleParcelable?> {
            return arrayOfNulls(size)
        }
    }
}