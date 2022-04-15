package com.borshevskiy.newsapp.domain

abstract class UseCase {

    abstract suspend operator fun invoke(): NetworkResult<List<News>>
}