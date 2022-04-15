package com.borshevskiy.newsapp.domain

import com.borshevskiy.newsapp.data.repository.NewsRepositoryImpl
import javax.inject.Inject

class LoadAppleNewsUseCase @Inject constructor(private val repository: NewsRepositoryImpl): UseCase() {

    override suspend operator fun invoke(): NetworkResult<List<News>> = repository.loadAppleNews()
}