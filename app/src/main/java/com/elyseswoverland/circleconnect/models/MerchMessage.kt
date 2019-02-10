package com.elyseswoverland.circleconnect.models

import com.google.gson.annotations.SerializedName

data class MerchMessage(@SerializedName("Subject")
                        val subject: String,
                        @SerializedName("Message")
                        val message: String,
                        @SerializedName("DateTimeStamp")
                        val timeStamp: String,
                        @SerializedName("MessageStatus")
                        val messageStatus: String)