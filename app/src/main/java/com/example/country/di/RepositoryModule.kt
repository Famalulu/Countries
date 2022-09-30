package com.example.country.di

import com.example.country.data.api.ApiService
import com.example.country.data.repository.MainRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    fun provideDataRepository(apiService: ApiService): MainRepository {
        return MainRepository(apiService)
    }
}