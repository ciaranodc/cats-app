package com.codc.cats.data.di

import android.content.Context
import androidx.room.Room
import com.codc.cats.data.source.local.database.AppDatabase
import com.codc.cats.data.source.local.database.dao.ImageDao
import com.codc.cats.data.source.local.database.migrations.MIGRATION_1_2
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(appContext, AppDatabase::class.java, "image-database")
            .addMigrations(MIGRATION_1_2)
            .build()
    }

    @Provides
    fun provideImageDao(appDatabase: AppDatabase): ImageDao {
        return appDatabase.imageDao()
    }
}