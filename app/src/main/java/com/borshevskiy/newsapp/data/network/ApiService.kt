package com.borshevskiy.newsapp.data.network

import com.borshevskiy.newsapp.data.network.model.AllArticlesDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.*

interface ApiService {

    @GET("everything")
    suspend fun getAppleNews(
        @Query(QUERY_PARAM_Q) q: String = Q_APPLE,
        @Query(QUERY_PARAM_FROM) from: String = FROM_APPLE,
        @Query(QUERY_PARAM_TO) to: String = TO,
        @Query(QUERY_PARAM_SORT_BY) sortBy: String = SORT_BY_APPLE,
        @Query(QUERY_PARAM_API_KEY) apiKey: String = API_KEY
    ): Response<AllArticlesDto>

    @GET("everything")
    suspend fun getTeslaNews(
        @Query(QUERY_PARAM_Q) q: String = Q_TESLA,
        @Query(QUERY_PARAM_FROM) from: String = FROM_TESLA,
        @Query(QUERY_PARAM_SORT_BY) sortBy: String = SORT_BY_TESLA,
        @Query(QUERY_PARAM_API_KEY) apiKey: String = API_KEY
    ): Response<AllArticlesDto>

    @GET("top-headlines")
    suspend fun getBusinessNews(
        @Query(QUERY_PARAM_COUNTRY) country: String = COUNTRY,
        @Query(QUERY_PARAM_CATEGORY) category: String = CATEGORY,
        @Query(QUERY_PARAM_API_KEY) apiKey: String = API_KEY
    ): Response<AllArticlesDto>

    @GET("top-headlines")
    suspend fun getTechCrunchNews(
        @Query(QUERY_PARAM_SOURCES) sources: String = SOURCES,
        @Query(QUERY_PARAM_API_KEY) apiKey: String = API_KEY
    ): Response<AllArticlesDto>

    @GET("everything")
    suspend fun getWallStreetNews(
        @Query(QUERY_PARAM_DOMAINS) domains: String = DOMAINS,
        @Query(QUERY_PARAM_API_KEY) apiKey: String = API_KEY
    ): Response<AllArticlesDto>

    companion object {
        private const val QUERY_PARAM_Q = "q"
        private const val QUERY_PARAM_TO = "to"
        private const val QUERY_PARAM_SORT_BY = "sortBy"
        private const val QUERY_PARAM_FROM = "from"
        private const val QUERY_PARAM_COUNTRY = "country"
        private const val QUERY_PARAM_CATEGORY = "category"
        private const val QUERY_PARAM_SOURCES = "sources"
        private const val QUERY_PARAM_DOMAINS = "domains"
        private const val QUERY_PARAM_API_KEY = "apiKey"
        private const val Q_APPLE = "apple"
        private const val Q_TESLA = "tesla"
        private const val FROM_APPLE = "2022-04-13"
        private var FROM_TESLA = "2022-03-${Calendar.getInstance().time.date}"
        private const val TO = "2022-04-13"
        private const val SORT_BY_APPLE = "popularity"
        private const val SORT_BY_TESLA = "publishedAt"
        private const val COUNTRY = "us"
        private const val CATEGORY = "business"
        private const val SOURCES = "techcrunch"
        private const val DOMAINS = "wsj.com"
        private const val API_KEY = "daea94329dc247f5aaa1e081a6e9ae00"
    }
}