package io.sellmair.kompass.android.example

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by sebastiansellmair on 27.01.18.
 */
@Parcelize
data class Contact(
    val name: String,
    val phone: String,
    val email: String?,
    val nickname: String?
) : Parcelable