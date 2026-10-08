package com.sadturtleman.kuit.data

import com.sadturtleman.kuit.BuildConfig
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

// ① 어떤 요청들이 있는가. Retrofit 이 이 interface 를 보고 구현체를 만들어 준다.
interface BookApi {

    // [정답 1] baseUrl + "volumes" + ?q=...&maxResults=...
    @GET("volumes")
    suspend fun searchBooks(
        @Query("q") query: String,
        @Query("maxResults") max: Int = 20,
        @Query("key") key: String = BuildConfig.BOOKS_API_KEY
    ): VolumesResponse

    // 상세 조회 (미션에서 사용). 그대로 두면 된다.
    @GET("volumes/{id}")
    suspend fun getBook(
        @Path("id") id: String,
        @Query("key") key: String = BuildConfig.BOOKS_API_KEY,
    ): VolumeDto
}
