package com.elyseswoverland.circleconnect.models

import com.google.gson.annotations.SerializedName

data class UpdateCustFavoritesRequest(@SerializedName("MerchId")
                                      val merchId: Int,
                                      @SerializedName("TurnOnOff")
                                      val turnOn: Boolean)