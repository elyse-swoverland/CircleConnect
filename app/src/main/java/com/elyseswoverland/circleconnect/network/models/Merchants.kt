package com.elyseswoverland.circleconnect.network.models

import com.google.gson.annotations.SerializedName

data class Merchants(@SerializedName("MerchId")
                     val merchId: Int,
                     @SerializedName("MerchType")
                     val merchType: MerchType,
                     @SerializedName("Name")
                     val merchName: String,
                     @SerializedName("Desc")
                     val description: String,
                     @SerializedName("City")
                     val city: String,
                     @SerializedName("StateCd")
                     val state: String,
                     @SerializedName("Zip")
                     val zipCode: String,
                     @SerializedName("MerchLocation")
                     val merchLocation: MerchLocation,
                     @SerializedName("Logo")
                     val logo: String? = null,
                     @SerializedName("MerchantRewards")
                     val merchantRewards: MerchantRewards,
                     @SerializedName("Active")
                     val active: Boolean) {
}