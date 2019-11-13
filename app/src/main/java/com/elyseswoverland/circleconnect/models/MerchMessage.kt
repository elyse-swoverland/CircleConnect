package com.elyseswoverland.circleconnect.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MerchMessage(@SerializedName("Subject")
                        val subject: String,
                        @SerializedName("Message")
                        val message: String,
                        @SerializedName("DateTimeStamp")
                        val timeStamp: String,
                        @SerializedName("MessageStatus")
                        val messageStatus: String?) : Parcelable