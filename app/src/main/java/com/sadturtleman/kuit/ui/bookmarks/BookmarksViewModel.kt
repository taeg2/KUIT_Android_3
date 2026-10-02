package com.sadturtleman.kuit.ui.bookmarks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sadturtleman.kuit.data.BookRepositoryImpl
import com.sadturtleman.kuit.domain.repository.BookRepository
import com.sadturtleman.kuit.ui.home.HomeState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class BookmarksViewModel(
    private val bookRepository: BookRepository
) : ViewModel() {
    private val _state = MutableStateFlow(BookmarksUiState(isLoading = true))  // 시작은 로딩 중
    val state = _state.asStateFlow()

    init {
        bookRepository.observeBookmarkedBooks()
            .onEach { books ->
                // 첫 값이 오면 로딩 끝 + 목록 채우기, 이후 변경 때도 계속 실행됨
                _state.update { it.copy(isLoading = false, books = books, error = null) }
            }
            .catch {
                _state.update { it.copy(isLoading = false, error = "error") }
            }
            .launchIn(viewModelScope)
    }

    fun toggleBookmark(id: Int) {
        bookRepository.toggleBookmark(id)   // 해제하면 Flow가 새 목록을 보내서 즉시 사라짐
    }
}