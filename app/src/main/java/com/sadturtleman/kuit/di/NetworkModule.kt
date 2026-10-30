package com.sadturtleman.kuit.di

import androidx.compose.foundation.style.apply
import com.sadturtleman.kuit.data.BookApi
import com.sadturtleman.kuit.data.BookService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import retrofit2.create
import javax.inject.Singleton
import kotlin.apply

// 생성자가 없는 객체(Retrofit, Service)는 여기서 "만드는 법"을 적는다.
// 4주차 data/Network.kt 의 코드가 그대로 옮겨 온다. Network.kt 는 삭제.
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private val json = Json {ignoreUnknownKeys = true}

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl("https://www.googleapis.com/books/v1/")
        .client(OkHttpClient.Builder().apply {}.build())
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .build()

    @Provides
    fun provideBookService(retrofit: Retrofit): BookService {
        return retrofit.create(BookService::class.java)
    }
}
