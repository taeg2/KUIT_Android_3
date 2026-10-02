package com.sadturtleman.kuit.data

import com.sadturtleman.kuit.domain.model.Book

data class BookDto(
    val book_id: Int,
    val book_title: String,
    val author_name: String,
    val page_count: Int,
    val published_year: Int,
    val book_description: String,
    val is_bookmarked: Boolean = false
)

// DTO → 도메인 모델 변환 (이름 차이를 여기서만 흡수)
fun BookDto.toBook(): Book = Book(
    id = book_id,
    title = book_title,
    author = author_name,
    isBookmarked = is_bookmarked,
    pages = page_count,
    year = published_year,
    description = book_description
)