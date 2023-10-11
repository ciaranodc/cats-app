package com.codc.cats.data.source.local.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_keys")
data class RemoteKeyEntity(
    @PrimaryKey
    val imageId: String,
    val prevKey: Int?,
    val nextKey: Int?
)