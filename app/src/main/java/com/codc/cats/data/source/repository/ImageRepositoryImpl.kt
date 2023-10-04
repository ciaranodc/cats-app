package com.codc.cats.data.source.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.codc.cats.data.api.CatApiService
import com.codc.cats.data.source.local.database.AppDatabase
import com.codc.cats.data.source.local.database.entity.ImageEntity
import com.codc.cats.data.source.remote.mediator.ImageRemoteMediator
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

private const val PAGE_SIZE = 10
private const val INITIAL_LOAD_SIZE = 10

@OptIn(ExperimentalPagingApi::class)
class ImageRepositoryImpl @Inject constructor(
    private val appDatabase: AppDatabase,
    private val catApiService: CatApiService
) : ImageRepository {

    override fun getImages(): Flow<PagingData<ImageEntity>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                initialLoadSize = INITIAL_LOAD_SIZE
            ),
            remoteMediator = ImageRemoteMediator(
                query = null,
                appDatabase = appDatabase,
                networkService = catApiService
            ),
            pagingSourceFactory = {
                appDatabase.imageDao().getAllImages()
            }
        ).flow
    }
}