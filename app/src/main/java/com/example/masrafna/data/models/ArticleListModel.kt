package com.example.masrafna.data.models


import com.google.gson.annotations.SerializedName
import java.util.*

data class ArticleListModel(
    @SerializedName("error")
    val error: Boolean,
    @SerializedName("message")
    val message: String,
    @SerializedName("payload")
    val payload: Payload
) {
    data class Payload(
        @SerializedName("data")
        val `data`: List<Data>,
        @SerializedName("links")
        val links: Links,
        @SerializedName("meta")
        val meta: Meta
    ) {
        data class Data(
            @SerializedName("content")
            val content: String,
            @SerializedName("created_at")
            val createdAt: Date,
            @SerializedName("id")
            val id: String,
            @SerializedName("image")
            val image: String,
            @SerializedName("title")
            val title: String,
            @SerializedName("updated_at")
            val updatedAt: Date
        )

        data class Links(
            @SerializedName("first")
            val first: String,
            @SerializedName("last")
            val last: String,
            @SerializedName("next")
            val next: String,
            @SerializedName("prev")
            val prev: Any
        )

        data class Meta(
            @SerializedName("current_page")
            val currentPage: Int,
            @SerializedName("from")
            val from: Int,
            @SerializedName("last_page")
            val lastPage: Int,
            @SerializedName("path")
            val path: String,
            @SerializedName("per_page")
            val perPage: Int,
            @SerializedName("to")
            val to: Int,
            @SerializedName("total")
            val total: Int
        )
    }
}