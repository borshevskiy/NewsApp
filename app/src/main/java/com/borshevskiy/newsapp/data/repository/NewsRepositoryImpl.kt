package com.borshevskiy.newsapp.data.repository

import com.borshevskiy.newsapp.data.mapper.NewsMapper
import com.borshevskiy.newsapp.data.network.ApiService
import com.borshevskiy.newsapp.data.network.model.AllArticlesDto
import com.borshevskiy.newsapp.domain.NetworkResult
import com.borshevskiy.newsapp.domain.News
import com.borshevskiy.newsapp.domain.NewsRepository
import retrofit2.Response
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(private val apiService: ApiService) : NewsRepository {

    private val mapper = NewsMapper()

    override suspend fun loadAppleNews(): NetworkResult<List<News>> = handleNewsResponse(apiService.getAppleNews())

    override suspend fun loadTeslaNews(): NetworkResult<List<News>>  = handleNewsResponse(apiService.getTeslaNews())

    override suspend fun loadBusinessNews(): NetworkResult<List<News>> = handleNewsResponse(apiService.getBusinessNews())

    override suspend fun loadTechCrunchNews(): NetworkResult<List<News>> = handleNewsResponse(apiService.getTechCrunchNews())

    override suspend fun loadWallStreetNews(): NetworkResult<List<News>> = handleNewsResponse(apiService.getWallStreetNews())

    private fun handleNewsResponse(response: Response<AllArticlesDto>): NetworkResult<List<News>> {
        return when {
            response.message().toString().contains("timeout") -> NetworkResult.Error("Timeout")
            response.code() == 402 -> NetworkResult.Error("API Key Limited.")
            response.body()!!.news.isNullOrEmpty() -> NetworkResult.Error("News not found")
            response.isSuccessful -> NetworkResult.Success(mapper.mapListNewsDtoToListNews(response.body()!!.news))
            else -> NetworkResult.Error(response.message())
        }
    }
}