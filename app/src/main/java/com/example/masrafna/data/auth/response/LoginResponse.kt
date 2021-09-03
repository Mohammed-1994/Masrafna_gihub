package com.example.masrafna.data.auth.response


import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("error")
    val error: Boolean,
    @SerializedName("message")
    val message: String,
    @SerializedName("payload")
    val payload: Payload
) {
    data class Payload(
        @SerializedName("access_token")
        val accessToken: AccessToken,
        @SerializedName("user")
        val user: User
    ) {
        data class AccessToken(
            @SerializedName("expire_at")
            val expireAt: String,
            @SerializedName("token")
            val token: String
        )

        data class User(
            @SerializedName("address")
            val address: Any,
            @SerializedName("birth")
            val birth: Any,
            @SerializedName("created_at")
            val createdAt: String,
            @SerializedName("email")
            val email: Any,
            @SerializedName("id")
            val id: Int,
            @SerializedName("image")
            val image: Any,
            @SerializedName("is_verified")
            val isVerified: Int,
            @SerializedName("name")
            val name: String,
            @SerializedName("phone")
            val phone: String,
            @SerializedName("role")
            val role: String
        )
    }
}