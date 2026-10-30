package com.sadturtleman.kuit.data

import com.sadturtleman.kuit.BuildConfig.BOOKS_API_KEY
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface BookApi {
    @GET("volumes")
    suspend fun searchBooks(
        @Query("q") query: String,
        @Query("maxResults") max: Int = 20,
        @Query("key") key: String = BOOKS_API_KEY,   // local.properties 대신 ...
    ): VolumeResponse

    @GET("volumes/{id}")
    suspend fun getBook(
        @Path("id") id: String,
        @Query("key") key: String = BOOKS_API_KEY,
    ): VolumeDto
}