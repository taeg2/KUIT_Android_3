package com.sadturtleman.kuit.data

import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

// ② 서버 주소 + JSON 변환기. 앱에 하나만 있으면 된다.
object Network {

    // 서버가 모르는 필드를 보내도 무시한다. (Google Books 는 필드가 수십 개)
    private val json = Json { ignoreUnknownKeys = true }

    // [정답 3]
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://www.googleapis.com/books/v1/")
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .build()

    val bookApi: BookApi = retrofit.create(BookApi::class.java)
}
