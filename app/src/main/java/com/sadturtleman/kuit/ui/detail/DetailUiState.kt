package com.sadturtleman.kuit.ui.detail

import com.sadturtleman.kuit.domain.model.Book

data class DetailUiState(
    val isLoading: Boolean = false,
    val book: Book? = null,
    val error: String? = null
)