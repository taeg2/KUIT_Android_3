package com.sadturtleman.kuit.ui.bookmarks

import com.sadturtleman.kuit.domain.model.Book
import kotlinx.coroutines.flow.Flow

data class BookmarksUiState(
    val isLoading: Boolean = false,
    val books: List<Book> = emptyList(),
    val error: String? = null
){
    val isEmpty: Boolean
        get() = books.isEmpty()
}