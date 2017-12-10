package io.sellmair.kompass.app

import android.os.Parcel
import android.os.Parcelable
import io.sellmair.kompass.annotation.Destination

/**
 * Created by sebastiansellmair on 10.12.17.
 */
@Destination
data class SimpleDestination(val id: Int, val name: String)

@Destination
class SimpleParcelDestination(val id: Int, val parcel: SimpleParcelable)

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