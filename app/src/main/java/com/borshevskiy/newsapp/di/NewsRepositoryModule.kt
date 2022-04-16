package com.borshevskiy.newsapp.di

import com.borshevskiy.newsapp.data.repository.NewsRepositoryImpl
import com.borshevskiy.newsapp.domain.NewsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class NewsRepositoryModule {

    @Binds
    abstract fun bindRepository(impl: NewsRepositoryImpl): NewsRepository
}