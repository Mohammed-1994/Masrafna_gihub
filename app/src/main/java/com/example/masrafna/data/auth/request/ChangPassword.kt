package com.example.masrafna.data.auth.request


import com.google.gson.annotations.SerializedName

data class ChangPassword(
    @SerializedName("phone")
    val phone: String
)