package com.sadturtleman.kuit.domain

import javax.inject.Inject

// 생성자가 있는 클래스는 @Inject constructor 만 붙이면 Hilt 가 만들 수 있다. 모듈 불필요. (제공)
class SearchBooksUseCase @Inject constructor(private val repo: BookRepository) {
    suspend operator fun invoke(query: String): List<Book> = repo.searchBooks(query)
}

class GetBookUseCase @Inject constructor(private val repo: BookRepository) {
    suspend operator fun invoke(id: String): Book? = repo.getBook(id)
}

class ToggleBookmarkUseCase @Inject constructor(private val repo: BookRepository) {
    suspend operator fun invoke(id: String) = repo.toggleBookmark(id)
}

class GetBookmarkedBooksUseCase @Inject constructor(private val repo: BookRepository) {
    suspend operator fun invoke(): List<Book> = repo.getBookmarkedBooks()
}
