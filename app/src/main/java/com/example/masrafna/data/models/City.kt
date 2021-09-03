package com.example.masrafna.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class City(
    var id: Int = 0,
    var name: String? = null,
) : Parcelable
