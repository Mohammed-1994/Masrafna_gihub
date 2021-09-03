package com.example.masrafna.data.models


import com.google.gson.annotations.SerializedName

data class BankDetails(
    @SerializedName("error")
    val error: Boolean,
    @SerializedName("message")
    val message: String,
    @SerializedName("payload")
    val payload: Payload
) {
    data class Payload(
        @SerializedName("about")
        val about: String,
        @SerializedName("cover")
        val cover: String,
        @SerializedName("created_at")
        val createdAt: String,
        @SerializedName("email")
        val email: String,
        @SerializedName("id")
        val id: String,
        @SerializedName("location")
        val location: String,
        @SerializedName("logo")
        val logo: String,
        @SerializedName("name")
        val name: String,
        @SerializedName("phone")
        val phone: String,
        @SerializedName("type")
        val type: Int,
        @SerializedName("updated_at")
        val updatedAt: String,
        @SerializedName("website")
        val website: String,
        @SerializedName("is_favorite")
        val isFavorite: Boolean
    )
}