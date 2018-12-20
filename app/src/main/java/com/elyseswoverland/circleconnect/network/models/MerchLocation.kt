package com.elyseswoverland.circleconnect.network.models

import com.google.gson.annotations.SerializedName
import java.math.BigDecimal

data class MerchLocation(@SerializedName("Latitude")
                         val latitude: BigDecimal,
                         @SerializedName("Longitude")
                         val longitude: BigDecimal,
                         @SerializedName("GeographyPoint")
                         val geographyPoint: Int) {
}