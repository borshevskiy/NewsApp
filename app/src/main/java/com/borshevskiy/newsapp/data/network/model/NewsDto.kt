package com.borshevskiy.newsapp.data.network.model

import com.google.gson.annotations.SerializedName

data class NewsDto(
    @SerializedName("author")
    val author: String?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("url")
    val url: String,
    @SerializedName("urlToImage")
    val urlToImage: String?,
    @SerializedName("publishedAt")
    val publishedAt: String,
    @SerializedName("content")
    val content: String,
    @SerializedName("source")
    val source: SourceDto
)