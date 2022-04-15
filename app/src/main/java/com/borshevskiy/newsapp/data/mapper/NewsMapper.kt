package com.borshevskiy.newsapp.data.mapper

import com.borshevskiy.newsapp.data.network.model.NewsDto
import com.borshevskiy.newsapp.domain.News

class NewsMapper {

    private fun mapNewsDtoToNews(newsDto: NewsDto): News = News(
        checkNull(newsDto.author),
        checkNull(newsDto.description),
        checkNull(newsDto.title),
        newsDto.url,
        newsDto.urlToImage,
        newsDto.publishedAt
    )

    fun mapListNewsDtoToListNews(listNewsDto: List<NewsDto>): List<News> {
        return listNewsDto.map {
            mapNewsDtoToNews(it)
        }
    }

    private fun checkNull(info: String?): String {
        return if (!info.isNullOrEmpty()) info else ""
    }
}