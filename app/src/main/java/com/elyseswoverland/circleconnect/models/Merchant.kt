package com.elyseswoverland.circleconnect.models

import com.google.gson.annotations.SerializedName

data class Merchant(@SerializedName("MerchId")
                    val merchId: Int,
                    @SerializedName("MerchType")
                    val merchType: MerchType,
                    @SerializedName("Name")
                    val merchName: String,
                    @SerializedName("Desc")
                    val description: String,
                    @SerializedName("Address")
                    val address: String?,
                    @SerializedName("City")
                    val city: String,
                    @SerializedName("StateCd")
                    val state: String,
                    @SerializedName("Zip")
                    val zipCode: String,
                    @SerializedName("DistanceFromCustomer")
                    val distanceFromCustomer: Double,
                    @SerializedName("MerchLocation")
                    val merchLocation: MerchLocation,
                    @SerializedName("Logo")
                    val logo: String? = null,
                    @SerializedName("MerchantRewards")
                    val merchantRewards: MerchantRewards,
                    @SerializedName("Active")
                    val active: Boolean)