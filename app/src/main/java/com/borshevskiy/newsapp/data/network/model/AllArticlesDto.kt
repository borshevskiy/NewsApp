package com.borshevskiy.newsapp.data.network.model

import com.google.gson.annotations.SerializedName

data class AllArticlesDto(
    @SerializedName("status")
    val status: String,
    @SerializedName("totalResults")
    val totalResults: Int,
    @SerializedName("articles")
    val news: List<NewsDto>
)