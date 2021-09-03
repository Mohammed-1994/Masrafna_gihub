package com.example.masrafna.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ServicesModel(
    var id: String? = null,
    var image: Int = 0,
    var title: String? = null,
    var desc: String? = null,
) : Parcelable
