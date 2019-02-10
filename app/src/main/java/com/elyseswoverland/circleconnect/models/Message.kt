package com.elyseswoverland.circleconnect.models

import com.google.gson.annotations.SerializedName

data class Message(@SerializedName("Merchants")
                   val merchant: Merchant,
                   @SerializedName("MerchMessage")
                   val merchMessage: MerchMessage)