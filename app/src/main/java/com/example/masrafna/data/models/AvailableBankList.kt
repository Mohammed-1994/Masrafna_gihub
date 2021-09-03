package com.example.masrafna.data.models


import com.google.gson.annotations.SerializedName
import java.util.*

data class AvailableBankList(
    @SerializedName("error")
    val error: Boolean,
    @SerializedName("message")
    val message: String,
    @SerializedName("payload")
    val payload: Payload
) {
    data class Payload(
        @SerializedName("about_loan")
        val aboutLoan: String,
        @SerializedName("available_for_banks")
        val availableForBanks: List<AvailableForBank>
    ) {
        data class AvailableForBank(
            @SerializedName("about")
            val about: String,
            @SerializedName("cover")
            val cover: String,
            @SerializedName("created_at")
            val createdAt: Date,
            @SerializedName("email")
            val email: String,
            @SerializedName("id")
            val id: String,
            @SerializedName("is_favorite")
            val isFavorite: Boolean,
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
            val updatedAt: Date,
            @SerializedName("website")
            val website: String
        )
    }
}