package com.borshevskiy.newsapp.domain

import javax.inject.Inject

class LoadAppleNewsUseCase @Inject constructor(private val repository: NewsRepository): UseCase() {

    override suspend operator fun invoke(): NetworkResult<List<News>> = repository.loadAppleNews()
}