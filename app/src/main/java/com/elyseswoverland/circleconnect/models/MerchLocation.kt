package com.elyseswoverland.circleconnect.models

import com.google.gson.annotations.SerializedName

data class MerchLocation(@SerializedName("Latitude")
                         val latitude: Double,
                         @SerializedName("Longitude")
                         val longitude: Double,
                         @SerializedName("GeographyPoint")
                         val geographyPoint: Int) {
}