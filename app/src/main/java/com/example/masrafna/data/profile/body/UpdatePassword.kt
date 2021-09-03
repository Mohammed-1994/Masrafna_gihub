package com.example.masrafna.data.profile.body


import com.google.gson.annotations.SerializedName

data class UpdatePassword(
    @SerializedName("old_password")
    val oldPassword: String,
    @SerializedName("password")
    val password: String
)