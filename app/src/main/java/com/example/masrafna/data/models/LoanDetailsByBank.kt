package com.example.masrafna.data.models


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class LoanDetailsByBank(
    @SerializedName("error")
    val error: Boolean,
    @SerializedName("message")
    val message: String,
    @SerializedName("payload")
    val payload: Payload
) : Parcelable {

    @Parcelize
    data class Payload(
        @SerializedName("about")
        val about: String,
        @SerializedName("bank_id")
        val bankId: String,
        @SerializedName("benefit")
        val benefit: String,
        @SerializedName("conditions")
        val conditions: String,
        @SerializedName("created_at")
        val createdAt: String,
        @SerializedName("id")
        val id: String,
        @SerializedName("limits")
        val limits: String,
        @SerializedName("loan_mechanism")
        val loanMechanism: String,
        @SerializedName("name")
        val name: String,
        @SerializedName("payment_mechanism")
        val paymentMechanism: String,
        @SerializedName("period")
        val period: String,
        @SerializedName("type")
        val type: Int,
        @SerializedName("updated_at")
        val updatedAt: String,
        @SerializedName("warranties")
        val warranties: String
    ) : Parcelable
}