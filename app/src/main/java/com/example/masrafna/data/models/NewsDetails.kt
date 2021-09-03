package com.example.masrafna.data.models


import com.google.gson.annotations.SerializedName
import java.util.*

data class NewsDetails(
    @SerializedName("error")
    val error: Boolean,
    @SerializedName("message")
    val message: String,
    @SerializedName("payload")
    val payload: Payload
) {
    data class Payload(
        @SerializedName("content")
        val content: String,
        @SerializedName("created_at")
        val createdAt: Date,
        @SerializedName("id")
        val id: String,
        @SerializedName("image")
        val image: String,
        @SerializedName("title")
        val title: String,
        @SerializedName("updated_at")
        val updatedAt: Date
    )
}