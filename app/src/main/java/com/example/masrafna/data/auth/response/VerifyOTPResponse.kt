package com.example.masrafna.data.auth.response


import com.google.gson.annotations.SerializedName

data class VerifyOTPResponse(
    @SerializedName("error")
    val error: Boolean,
    @SerializedName("message")
    val message: String,
    @SerializedName("payload")
    val payload: Payload
) {
    data class Payload(
        @SerializedName("user")
        val user: User
    ) {
        data class User(
            @SerializedName("address")
            val address: String,
            @SerializedName("birth")
            val birth: String,
            @SerializedName("id")
            val id: Int,
            @SerializedName("is_verified")
            val isVerified: Boolean,
            @SerializedName("name")
            val name: String,
            @SerializedName("phone")
            val phone: String,
            @SerializedName("role")
            val role: String
        )
    }
}