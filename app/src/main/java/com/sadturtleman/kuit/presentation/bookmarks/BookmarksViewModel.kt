package com.sadturtleman.kuit.presentation.bookmarks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sadturtleman.kuit.domain.GetBookmarkedBooksUseCase
import com.sadturtleman.kuit.domain.ToggleBookmarkUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

// [TODO 4-b] HomeViewModel 과 동일하게.
@HiltViewModel
class BookmarksViewModel @Inject constructor(
    getBookmarkedBooks: GetBookmarkedBooksUseCase,
    private val toggleBookmark: ToggleBookmarkUseCase,
) : ViewModel() {

    val state: StateFlow<BookmarksUiState> = getBookmarkedBooks()
        .map { books -> BookmarksUiState(books = books) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = BookmarksUiState(isLoading = true),
        )

    fun onIntent(intent: BookmarksIntent) {
        when (intent) {
            is BookmarksIntent.ToggleBookmark -> toggleBookmark(intent.id)   // 해제하면 Flow 가 알아서 목록 갱신
        }
    }
}
