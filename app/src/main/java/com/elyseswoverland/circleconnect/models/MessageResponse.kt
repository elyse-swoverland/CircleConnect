package com.elyseswoverland.circleconnect.models

import com.google.gson.annotations.SerializedName

data class MessageResponse(@SerializedName("NewMessages")
                           val newMessages: Int,
                           @SerializedName("Messages")
                           val messages: ArrayList<Message>)