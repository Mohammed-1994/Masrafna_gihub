package com.example.masrafna.data.auth.request

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class VerifyOTPBody(
    var otp: String? = null,
): Parcelable