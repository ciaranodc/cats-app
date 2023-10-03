package com.codc.cats.data.di

import com.codc.cats.data.api.ImageApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

//    @Binds
//    fun provideImageRepository(impl: RemoteImageRepository): ImageRepository

    @Singleton
    @Provides
    fun provideImagesApiService(): ImageApiService {
        return ImageApiService.create()
    }
}