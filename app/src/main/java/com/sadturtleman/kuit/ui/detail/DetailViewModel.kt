package com.sadturtleman.kuit.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sadturtleman.kuit.domain.repository.BookRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DetailViewModel(
    private val bookId: Int,
    private val bookRepository: BookRepository
) : ViewModel() {
    private val _state = MutableStateFlow(DetailUiState())
    val state = _state.asStateFlow()

    init {
        // 다른 화면에서 북마크가 바뀌어도 최신 상태 유지
        bookRepository.observeBooks()
            .onEach { books ->
                val book = books.firstOrNull { it.id == bookId }
                if (book != null) _state.update { it.copy(book = book) }
            }
            .launchIn(viewModelScope)

        load()
    }

    fun load() = viewModelScope.launch {
        _state.update { it.copy(isLoading = true, error = null) }
        runCatching { bookRepository.getBook(bookId) }.onSuccess { book ->
            if (book == null) {
                _state.update { it.copy(isLoading = false, error = "책을 찾을 수 없어요") }
            } else {
                _state.update { it.copy(isLoading = false, book = book) }
            }
        }.onFailure {
            _state.update { it.copy(isLoading = false, error = "에러") }
        }
    }

    fun toggleBookmark() {
        bookRepository.toggleBookmark(bookId)   // 구독 중이라 state는 자동 갱신
    }
}