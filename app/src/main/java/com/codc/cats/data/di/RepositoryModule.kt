package com.codc.cats.data.di

import com.codc.cats.data.source.repository.ImageRepository
import com.codc.cats.data.source.repository.ImageRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideImageRepository(impl: ImageRepositoryImpl): ImageRepository
}