package com.example.masrafna.data.auth.response


import com.google.gson.annotations.SerializedName

data class ResetPasswordVerifyOTP(
    @SerializedName("error")
    val error: Boolean,
    @SerializedName("message")
    val message: String,
    @SerializedName("payload")
    val payload: Payload
) {
    data class Payload(
        @SerializedName("access_token")
        val accessToken: AccessToken
    ) {
        data class AccessToken(
            @SerializedName("expire_at")
            val expireAt: String,
            @SerializedName("token")
            val token: String
        )
    }
}