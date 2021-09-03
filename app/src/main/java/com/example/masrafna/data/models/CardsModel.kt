package com.example.masrafna.data.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class CardsModel(
    var image: Int = 0,
    var title: String? = null,
    var desc: String? = null,
    var date: String? = null
): Parcelable