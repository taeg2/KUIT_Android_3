package com.sadturtleman.kuit.domain

// 3주차와 동일. id 만 String 으로 바뀜 (Google Books 의 id 가 문자열이라서)
data class Book(
    val id: String,
    val title: String,
    val author: String,
    val pages: Int? = null,
    val year: String? = null,
    val description: String? = null,
)

interface BookRepository {
    suspend fun searchBooks(query: String): List<Book>
    suspend fun getBook(id: String): Book?
}

class GetBooksUseCase(private val repo: BookRepository) {
    suspend operator fun invoke(query: String): List<Book> = repo.searchBooks(query)
}

class GetBookUseCase(private val repo: BookRepository) {
    suspend operator fun invoke(id: String): Book? = repo.getBook(id)
}
