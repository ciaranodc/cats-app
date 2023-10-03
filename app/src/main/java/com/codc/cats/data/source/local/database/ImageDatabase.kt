package com.codc.cats.data.source.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.codc.cats.data.source.local.database.dao.AuthorDao
import com.codc.cats.data.source.local.database.dao.ImageDao
import com.codc.cats.data.source.local.database.dao.RemoteKeysDao
import com.codc.cats.data.source.local.database.entity.AuthorEntity
import com.codc.cats.data.source.local.database.entity.ImageEntity
import com.codc.cats.data.source.local.database.entity.RemoteKeysEntity

@Database(
    entities = [ImageEntity::class, AuthorEntity::class, RemoteKeysEntity::class],
    version = 1
)
abstract class ImageDatabase : RoomDatabase() {
    abstract fun imageDao(): ImageDao
    abstract fun authorDao(): AuthorDao
    abstract fun remoteKeysDao(): RemoteKeysDao
}