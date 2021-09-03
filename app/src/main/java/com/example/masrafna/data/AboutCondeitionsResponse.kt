package com.example.masrafna.data


import com.google.gson.annotations.SerializedName

data class AboutCondeitionsResponse(
    @SerializedName("error")
    val error: Boolean,
    @SerializedName("message")
    val message: String,
    @SerializedName("payload")
    val payload: Payload
) {
    data class Payload(
        @SerializedName("text")
        val text: String
    )
}