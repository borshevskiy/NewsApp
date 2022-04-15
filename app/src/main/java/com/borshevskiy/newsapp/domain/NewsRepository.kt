package com.borshevskiy.newsapp.domain

interface NewsRepository {

    suspend fun loadAppleNews(): NetworkResult<List<News>>

    suspend fun loadTeslaNews(): NetworkResult<List<News>>

    suspend fun loadBusinessNews(): NetworkResult<List<News>>

    suspend fun loadTechCrunchNews(): NetworkResult<List<News>>

    suspend fun loadWallStreetNews(): NetworkResult<List<News>>
}