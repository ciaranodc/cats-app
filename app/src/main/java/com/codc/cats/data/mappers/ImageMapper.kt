package com.codc.cats.data.mappers

import com.codc.cats.data.model.Image
import com.codc.cats.data.source.local.database.entity.ImageEntity

fun ImageEntity.toDomainModel() = Image(id, url)