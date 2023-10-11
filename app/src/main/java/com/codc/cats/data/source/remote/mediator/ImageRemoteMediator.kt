package com.codc.cats.data.source.remote.mediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.codc.cats.data.api.CatApiService
import com.codc.cats.data.source.local.database.AppDatabase
import com.codc.cats.data.source.local.database.entity.ImageEntity
import com.codc.cats.data.source.local.database.entity.RemoteKeyEntity
import retrofit2.HttpException
import java.io.IOException

// The Cat API pages start at 0: https://developers.thecatapi.com
private const val CAT_API_STARTING_PAGE_INDEX = 0

@OptIn(ExperimentalPagingApi::class)
class ImageRemoteMediator(
    private val query: String?,
    private val appDatabase: AppDatabase,
    private val networkService: CatApiService
) : RemoteMediator<Int, ImageEntity>() {

    override suspend fun initialize(): InitializeAction {
        // Launch remote refresh as soon as paging starts and do not trigger remote prepend or
        // append until refresh has succeeded. In cases where we don't mind showing out-of-date,
        // cached offline data, we can return SKIP_INITIAL_REFRESH instead to prevent paging
        // triggering remote refresh.
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ImageEntity>
    ): MediatorResult {

        val currentPage = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKey = getRemoteKeyClosestToCurrentPosition(state)
                remoteKey?.nextKey?.minus(1) ?: CAT_API_STARTING_PAGE_INDEX
            }

            LoadType.PREPEND -> {
                val remoteKey = getRemoteKeyForFirstItem(state)
                val prevKey = remoteKey?.prevKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKey != null)
                prevKey
            }

            LoadType.APPEND -> {
                val remoteKey = getRemoteKeyForLastItem(state)
                val nextKey = remoteKey?.nextKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKey != null)
                nextKey
            }
        }

        try {
            // If fetching images by query (e.g. cat breed) then don't worry about pagination
            if (query != null) {
                return MediatorResult.Success(
                    endOfPaginationReached = true
                )
            }

            val images = networkService.getImagesList(
                page = currentPage,
                limit = state.config.pageSize
            )

            val endOfPaginationReached = images.isEmpty()

            appDatabase.withTransaction {
                // clear all tables in the database
                if (loadType == LoadType.REFRESH) {
                    appDatabase.remoteKeyDao().clearRemoteKeys()
                    appDatabase.imageDao().clearAll()
                }
                val prevKey =
                    if (currentPage == CAT_API_STARTING_PAGE_INDEX) null else currentPage - 1
                val nextKey = if (endOfPaginationReached) null else currentPage + 1
                val keys = images.map {
                    RemoteKeyEntity(imageId = it.id, prevKey = prevKey, nextKey = nextKey)
                }

                appDatabase.remoteKeyDao().insertAll(keys)
                appDatabase.imageDao().upsertAll(images)
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, ImageEntity>): RemoteKeyEntity? {
        // Get the last page that was retrieved, that contained items.
        // From that last page, get the last item
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { imageEntity ->
                // Get the remote keys of the last item retrieved
                appDatabase.remoteKeyDao().remoteKeyByImageId(imageEntity.id)
            }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, ImageEntity>): RemoteKeyEntity? {
        // Get the first page that was retrieved, that contained items.
        // From that first page, get the first item
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { imageEntity ->
                // Get the remote keys of the first items retrieved
                appDatabase.remoteKeyDao().remoteKeyByImageId(imageEntity.id)
            }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, ImageEntity>
    ): RemoteKeyEntity? {
        // The paging library is trying to load data after the anchor position
        // Get the item closest to the anchor position
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { imageId ->
                appDatabase.remoteKeyDao().remoteKeyByImageId(imageId)
            }
        }
    }
}