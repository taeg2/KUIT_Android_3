package com.sadturtleman.kuit.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sadturtleman.kuit.data.BookRepositoryImpl
import com.sadturtleman.kuit.domain.repository.BookRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val bookRepository: BookRepository
) : ViewModel() {
    private val _state = MutableStateFlow(HomeState())
    val state = _state.asStateFlow()



    init {
        bookRepository.observeBooks()
            .onEach { books -> _state.update { it.copy(books = books) } }
            .launchIn(viewModelScope)
        load()
    }

    fun load() = viewModelScope.launch {
        _state.update { it.copy(isLoading = true, error = null) }
        runCatching { bookRepository.getBooks() }.onSuccess { books ->
            _state.update { it.copy(isLoading = false, books = books) }
        }.onFailure {
            _state.update { it.copy(isLoading = false, error = "에러") }
        }
    }

    fun toggleBookmark(id: Int) {
        bookRepository.toggleBookmark(id)
    }
}