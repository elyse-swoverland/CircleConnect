package com.elyseswoverland.circleconnect.models

import com.google.gson.annotations.SerializedName

data class SortPreferenceType(@SerializedName("SortPreferenceCd")
                              var sortPreferenceCode: Int,
                              @SerializedName("SortPreferenceDesc")
                              var sortPreferenceDescription: String) {
}