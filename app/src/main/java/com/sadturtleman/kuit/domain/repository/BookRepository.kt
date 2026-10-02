package com.sadturtleman.kuit.domain.repository

import com.sadturtleman.kuit.domain.model.Book
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

interface BookRepository {
    suspend fun getBooks(): List<Book>
    suspend fun getBook(id: Int): Book?
    fun toggleBookmark(id: Int): List<Book>
    fun observeBooks(): Flow<List<Book>>
    fun observeBookmarkedBooks(): Flow<List<Book>>
}