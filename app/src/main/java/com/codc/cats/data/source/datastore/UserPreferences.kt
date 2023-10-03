package com.codc.cats.data.source.datastore

import kotlinx.coroutines.flow.Flow

interface UserPreferences {
    fun getStoredData(prefKey: String): Flow<String>

    suspend fun storeData(prefKey: String, data: String)
}