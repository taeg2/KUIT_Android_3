package com.sadturtleman.kuit.domain

import kotlinx.coroutines.flow.Flow

// interface 는 Domain 에. 어디서 가져오는지(Fake / 서버 / DB)는 모른다.
interface BookRepository {
    fun searchBooks(query: String): Flow<List<Book>>
    suspend fun getBook(id: String): Book
    fun toggleBookmark(id: String)
    fun getBookmarkedBooks(): Flow<List<Book>>
}
