package com.elyseswoverland.circleconnect.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MerchLocation(@SerializedName("Latitude")
                         val latitude: Double,
                         @SerializedName("Longitude")
                         val longitude: Double,
                         @SerializedName("GeographyPoint")
                         val geographyPoint: Int) : Parcelable