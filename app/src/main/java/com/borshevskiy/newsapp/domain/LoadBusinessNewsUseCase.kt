package com.borshevskiy.newsapp.domain

import javax.inject.Inject

class LoadBusinessNewsUseCase @Inject constructor(private val repository: NewsRepository): UseCase()  {

    override suspend operator fun invoke(): NetworkResult<List<News>> = repository.loadBusinessNews()
}