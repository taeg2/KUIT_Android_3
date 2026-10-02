package com.sadturtleman.kuit.ui.home

import com.sadturtleman.kuit.domain.model.Book

data class HomeState(
    val isLoading: Boolean = false,
    val books: List<Book> = emptyList(),
    val error: String? = null
){
    val bookmarkCount: Int = books.count { it.isBookmarked }
}