package com.example.masrafna.data.models


import com.google.gson.annotations.SerializedName
import java.util.*

data class NotificationModel(
    @SerializedName("error")
    val error: Boolean,
    @SerializedName("message")
    val message: String,
    @SerializedName("payload")
    val notifications: List<Notifications>
) {
    data class Notifications(
        @SerializedName("created_at")
        val createdAt: Date,
        @SerializedName("id")
        val id: String,
        @SerializedName("image")
        val image: String,
        @SerializedName("text")
        val text: String,
        @SerializedName("title")
        val title: String
    )
}