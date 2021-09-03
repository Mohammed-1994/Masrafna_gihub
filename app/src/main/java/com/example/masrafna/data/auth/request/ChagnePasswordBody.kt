package com.example.masrafna.data.auth.request


import com.google.gson.annotations.SerializedName

data class ChagnePasswordBody(
    @SerializedName("password")
    val password: String
)