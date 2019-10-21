package com.elyseswoverland.circleconnect.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
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
                    val active: Boolean,
                    @SerializedName("Email")
                    val merchEmail: String,
                    @SerializedName("ContactName")
                    val contactName: String?,
                    @SerializedName("MobilePhone")
                    val mobilePhone: String? = null,
                    @SerializedName("BusinessPhone")
                    val businessPhone: String? = null,
                    @SerializedName("FaceBookPage")
                    val facebookPage: String,
                    @SerializedName("WebsiteURL")
                    val website: String,
                    @SerializedName("CustFavorite")
                    val custFavorite: Boolean) : Parcelable