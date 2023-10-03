package com.codc.cats.data.source.repository

import androidx.paging.PagingData
import com.codc.cats.data.source.local.database.entity.AuthorEntity
import com.codc.cats.data.source.local.database.entity.ImageEntity
import kotlinx.coroutines.flow.Flow

interface ImageRepository {
    fun getImages(author: String? = null): Flow<PagingData<ImageEntity>>

    suspend fun updateAuthorSelection(author: AuthorEntity?)

    val authors: Flow<List<AuthorEntity>>

    val selectedAuthor: Flow<AuthorEntity?>
}