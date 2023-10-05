package com.codc.cats.data.api

import com.codc.cats.BuildConfig
import com.codc.cats.data.source.local.database.entity.ImageEntity
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface CatApiService {

    @Headers("x-api-key: ${BuildConfig.CAT_API_KEY}")
    @GET("search")
    suspend fun getImagesList(
        @Query("page") page: Int,
        @Query("limit") limit: Int,
    ): List<ImageEntity>

    companion object {
        private const val BASE_URL = "https://api.thecatapi.com/v1/images/"

        fun create(): CatApiService {
            val catApiService: CatApiService by lazy {
                val interceptor = HttpLoggingInterceptor()
                interceptor.level = HttpLoggingInterceptor.Level.BODY
                val client = OkHttpClient().newBuilder().addInterceptor(interceptor).build()

                Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build()
                    .create(CatApiService::class.java)
            }

            return catApiService
        }
    }
}