package com.sadturtleman.kuit.presentation.home

import com.sadturtleman.kuit.domain.Book


data class HomeUiState(
    val isLoading: Boolean = false,
    val books: List<Book> = emptyList(),
    val error: String? = null,
    val query: String = "",   // 검색이 "실행된" 검색어. 입력 중인 글자는 화면의 remember 가 가진다
)

sealed interface HomeIntent {
    data object Refresh : HomeIntent
    data class Search(val query: String) : HomeIntent
}
