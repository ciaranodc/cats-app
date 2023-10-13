package com.codc.cats.data.source

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.codc.cats.TestUtils
import com.codc.cats.data.source.local.database.entity.ImageEntity
import com.codc.cats.data.source.repository.ImageRepository
import com.codc.sharedtest.FakePagingSource
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow

class FakeImageRepository : ImageRepository {
    override fun getImages(): Flow<PagingData<ImageEntity>> {
        val rawJson = TestUtils().getRawJsonFromFile("raw/json/images_response.json")
        val imagesList = Gson().fromJson(rawJson, Array<ImageEntity>::class.java).asList()

        return Pager(
            PagingConfig(pageSize = 20)
        ) {
            FakePagingSource(imagesList)
        }.flow
    }
}