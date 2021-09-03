package com.example.masrafna.data.auth.request


import com.google.gson.annotations.SerializedName

data class LoginBody(
    @SerializedName("password")
    val password: String,
    @SerializedName("phone")
    val phone: String
)