package com.elyseswoverland.circleconnect.network.models

import com.google.gson.annotations.SerializedName

data class SessionResponse(@SerializedName("CustomerId")
                           val customerId: Int,
                           @SerializedName("IsNewUser")
                           val isNewUser: Boolean,
                           @SerializedName("JWTToken")
                           val token: String) {
}