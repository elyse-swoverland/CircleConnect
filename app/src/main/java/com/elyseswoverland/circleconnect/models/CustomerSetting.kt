package com.elyseswoverland.circleconnect.models

import com.google.gson.annotations.SerializedName

data class CustomerSetting(@SerializedName("CustomerId")
                           var customerId: Int,
                           @SerializedName("SortPreferenceCd")
                           var sortPreferenceCode: Int,
                           @SerializedName("CategoryFilterArtsEnter")
                           var filterArts: Boolean,
                           @SerializedName("CategoryFilterFood")
                           var filterFood: Boolean,
                           @SerializedName("CategoryFilterRetail")
                           var filterRetail: Boolean,
                           @SerializedName("CategoryFilterService")
                           var filterService: Boolean,
                           @SerializedName("DistanceValue")
                           var distanceValue: Int)