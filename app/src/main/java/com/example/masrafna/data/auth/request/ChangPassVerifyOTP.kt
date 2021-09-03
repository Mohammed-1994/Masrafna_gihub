package com.example.masrafna.data.auth.request


import com.google.gson.annotations.SerializedName

data class ChangPassVerifyOTP(
    @SerializedName("phone")
    val phone: String,
    @SerializedName("otp")
    val otp: String

)