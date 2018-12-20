package com.elyseswoverland.circleconnect.models

import com.google.gson.annotations.SerializedName

data class MerchantRewards(@SerializedName("Rewards")
                           val rewards: Int) {
}