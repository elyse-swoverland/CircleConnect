package com.elyseswoverland.circleconnect.models

import com.google.gson.annotations.SerializedName

data class SessionRequest(@SerializedName("OAuthId")
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