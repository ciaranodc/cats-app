package com.codc.cats.data.di

import com.codc.cats.data.api.CatApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Singleton
    @Provides
    fun provideImagesApiService(): CatApiService {
        return CatApiService.create()
    }
}