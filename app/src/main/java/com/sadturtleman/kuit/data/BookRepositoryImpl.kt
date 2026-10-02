package com.sadturtleman.kuit.data

import com.sadturtleman.kuit.domain.model.Book
import com.sadturtleman.kuit.domain.repository.BookRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlin.collections.map

class BookRepositoryImpl : BookRepository {
    private val BookList = MutableStateFlow(
        listOf(
            BookDto(1, "Kotlin in Action", "Dmitry Jemerov", 360, 2017,
                "코틀린 언어 설계자들이 직접 쓴 코틀린 입문서"),
            BookDto(2, "Clean Architecture", "Robert C. Martin", 432, 2017,
                "소프트웨어 구조와 설계 원칙을 다루는 책. 의존성 규칙, 경계, 계층 분리에 대해 설명하며, 이번 주 세미나에서 다룬 Layered Architecture의 원전에 가깝다."),
            BookDto(3, "Jetpack Compose 완벽 가이드", "KUIT", 300, 2024,
                "선언형 UI의 기본부터 상태 관리까지"),
            BookDto(4, "Refactoring UI", "Adam Wathan", 218, 2018,
                "개발자를 위한 실전 UI 디자인 팁"),
            BookDto(5, "Effective Kotlin", "Marcin Moskała", 400, 2019,
                "더 안전하고 읽기 좋은 코틀린 코드를 위한 규칙들"),
            BookDto(6, "Head First Design Patterns", "Eric Freeman", 694, 2004,
                "그림과 예제로 배우는 디자인 패턴")
        )
    )
    override suspend fun getBooks(): List<Book> {
        return BookList.value.map { it.toBook() }
    }

    override suspend fun getBook(id: Int): Book? {
        return BookList.value.firstOrNull { it.book_id == id }?.toBook()
    }

    override fun toggleBookmark(id: Int): List<Book> {
        BookList.update { dtos ->
            dtos.map { dto ->
                if (dto.book_id == id) dto.copy(is_bookmarked = !dto.is_bookmarked) else dto
            }
        }
        return BookList.value.map { it.toBook() }
    }


    override fun observeBooks(): Flow<List<Book>> {
        return BookList.map { dtos -> dtos.map { it.toBook() } }
    }

    override fun observeBookmarkedBooks(): Flow<List<Book>> {
        return BookList.map { dtos ->
            dtos.filter { it.is_bookmarked }
                .map { it.toBook() }
        }
    }


}