package com.elyseswoverland.circleconnect.network.models

import com.google.gson.annotations.SerializedName

data class Session(@SerializedName("OAuthId")
                   var oAuthId: String,
                   @SerializedName("LoginType")
                   var loginType: String,
                   @SerializedName("FirstName")
                   var firstName: String,
                   @SerializedName("LastName")
                   var lastName: String,
                   @SerializedName("Email")
                   var email: String,
                   @SerializedName("Zip")
                   var zipCode: Int,
                   @SerializedName("Doc")
                   var doc: String?) {
}