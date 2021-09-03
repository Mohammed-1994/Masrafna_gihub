package com.example.masrafna.data.profile.response


import com.google.gson.annotations.SerializedName

data class UpdatePassResponse(
    @SerializedName("error")
    val error: Boolean,
    @SerializedName("message")
    val message: String,
    @SerializedName("payload")
    val payload: Payload
) {
    data class Payload(
        @SerializedName("oauth")
        val oauth: String
    )
}