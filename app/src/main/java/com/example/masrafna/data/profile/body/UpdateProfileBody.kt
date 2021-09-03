package com.example.masrafna.data.profile.body


import com.google.gson.annotations.SerializedName

data class UpdateProfileBody(
    @SerializedName("address")
    val address: String,
    @SerializedName("birth")
    val birth: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("name")
    val name: String
)