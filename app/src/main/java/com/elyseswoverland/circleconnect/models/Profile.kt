package com.elyseswoverland.circleconnect.models

import com.google.gson.annotations.SerializedName

data class Profile(@SerializedName("FirstName")
                   var firstName: String,
                   @SerializedName("LastName")
                   var lastName: String,
                   @SerializedName("Email")
                   var email: String,
                   @SerializedName("Zip")
                   var zipCode: String,
                   @SerializedName("Birthday")
                   var birthday: String)