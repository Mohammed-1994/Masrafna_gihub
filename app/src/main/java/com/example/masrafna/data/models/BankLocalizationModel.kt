package com.example.masrafna.data.models


import com.google.gson.annotations.SerializedName

data class BankLocalizationModel(
    @SerializedName("error")
    val error: Boolean,
    @SerializedName("message")
    val message: String,
    @SerializedName("payload")
    val payload: Payload
) {
    data class Payload(
        @SerializedName("id")
        val id: String,
        @SerializedName("text")
        val text: String
    )
}