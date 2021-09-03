package com.example.masrafna.data.auth.request


import com.google.gson.annotations.SerializedName

data class AddToFavoriteModel(
    @SerializedName("bank_id")
    val bankId: String
)