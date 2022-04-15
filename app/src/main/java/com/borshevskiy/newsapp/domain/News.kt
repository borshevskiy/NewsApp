package com.borshevskiy.newsapp.domain

data class News(
    val author: String,
    val description: String,
    val title: String,
    val url: String,
    val urlToImage: String?,
    val publishedAt: String
)