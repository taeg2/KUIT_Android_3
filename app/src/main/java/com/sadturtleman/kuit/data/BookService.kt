package com.sadturtleman.kuit.data

import com.sadturtleman.kuit.BuildConfig
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

// 4주차 BookApi → BookService. 이름만 바뀐다.
// 책임: HTTP 요청 선언. 그 외 로직 금지.
interface BookService {

    @GET("volumes")
    suspend fun searchBooks(
        @Query("q") query: String,
        @Query("maxResults") max: Int = 20,
        @Query("key") key: String = BuildConfig.BOOKS_API_KEY,   // local.properties 에서. 코드에 직접 쓰지 않는다
    ): VolumeResponse

    @GET("volumes/{id}")
    suspend fun getBook(
        @Path("id") id: String,
        @Query("key") key: String = BuildConfig.BOOKS_API_KEY,
    ): VolumeDto
}
