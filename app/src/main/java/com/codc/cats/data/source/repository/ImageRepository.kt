package com.codc.cats.data.source.repository

import androidx.paging.PagingData
import com.codc.cats.data.source.local.database.entity.ImageEntity
import kotlinx.coroutines.flow.Flow

interface ImageRepository {
    fun getImages(): Flow<PagingData<ImageEntity>>
}