package com.codc.cats.data.source.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.codc.cats.data.source.local.database.dao.ImageDao
import com.codc.cats.data.source.local.database.dao.RemoteKeyDao
import com.codc.cats.data.source.local.database.entity.ImageEntity
import com.codc.cats.data.source.local.database.entity.RemoteKeyEntity

@Database(
    entities = [ImageEntity::class, RemoteKeyEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun imageDao(): ImageDao
    abstract fun remoteKeysDao(): RemoteKeyDao
}