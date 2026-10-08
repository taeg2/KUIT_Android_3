package com.sadturtleman.kuit.data

import com.sadturtleman.kuit.data.BookApi
import com.sadturtleman.kuit.domain.Book
import com.sadturtleman.kuit.domain.BookRepository


// 3주차 FakeBookRepository 를 대신한다. interface 는 그대로, 구현만 바뀐다.
class BookRepositoryImpl(private val api: BookApi) : BookRepository {

    // [정답 4] 서버 응답(DTO) → 도메인 모델. 바뀌는 건 Data 계층뿐.
    override suspend fun searchBooks(query: String): List<Book> =
        api.searchBooks(query).items.map { it.toBook() }

    override suspend fun getBook(id: String): Book? =
        api.getBook(id).toBook()
}
