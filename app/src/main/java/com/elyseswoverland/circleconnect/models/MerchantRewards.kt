package com.elyseswoverland.circleconnect.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MerchantRewards(@SerializedName("Rewards")
                           val rewards: Int) : Parcelable {
}