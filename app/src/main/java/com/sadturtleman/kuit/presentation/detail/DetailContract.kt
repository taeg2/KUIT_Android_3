package com.sadturtleman.kuit.presentation.detail

import com.sadturtleman.kuit.domain.Book

data class DetailUiState(
    val isLoading: Boolean = false,
    val book: Book? = null,
    val error: String? = null,
)

sealed interface DetailIntent {
    data class Load(val id: String) : DetailIntent
}
