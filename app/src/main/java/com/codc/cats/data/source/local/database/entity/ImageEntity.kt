package com.codc.cats.data.source.local.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "image")
data class ImageEntity(
    @PrimaryKey
    @ColumnInfo("id")
    val id: String,
    @ColumnInfo("url")
    val url: String,
    @ColumnInfo("width")
    val width: Int,
    @ColumnInfo("height")
    val height: Int
)