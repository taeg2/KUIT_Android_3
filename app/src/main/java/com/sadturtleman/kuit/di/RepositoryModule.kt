package com.sadturtleman.kuit.di

import com.sadturtleman.kuit.data.BookRepositoryImpl
import com.sadturtleman.kuit.domain.BookRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

// interface(BookRepository) 를 요청하면 구현(BookRepositoryImpl) 을 주라는 연결.
// 4주차 data/AppContainer.kt 의 역할. AppContainer.kt 는 삭제.
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindBookRepository(impl: BookRepositoryImpl): BookRepository
}
