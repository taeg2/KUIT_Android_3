package com.sadturtleman.kuit.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sadturtleman.kuit.domain.SearchBooksUseCase
import com.sadturtleman.kuit.domain.ToggleBookmarkUseCase
import com.sadturtleman.kuit.presentation.toUserMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.net.UnknownHostException
import javax.inject.Inject

// [TODO 4-a] @HiltViewModel + @Inject constructor.
//            생성자 인자(UseCase)는 Hilt 가 채운다. App.kt 에서 더 이상 만들지 않는다.
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val searchBooks: SearchBooksUseCase,
    private val toggleBookmark: ToggleBookmarkUseCase,
) : ViewModel() {

    private val query = MutableStateFlow(HomeUiState().query)
    private val refreshTrigger = MutableStateFlow(0)

    val state: StateFlow<HomeUiState> = combine(query, refreshTrigger) { q, _ -> q }
        .flatMapLatest { q ->
            searchBooks(q)
                .map { books -> HomeUiState(query = q, books = books) }
                .onStart { emit(HomeUiState(query = q, isLoading = true)) }
                .catch { e -> emit(HomeUiState(query = q, error = e.toUserMessage())) }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = HomeUiState(isLoading = true),
        )

    fun onIntent(intent: HomeIntent) {
        when (intent) {
            HomeIntent.Refresh -> refreshTrigger.update { it + 1 }
            is HomeIntent.Search -> {
                if (intent.query.isBlank()) return
                query.value = intent.query
            }
            is HomeIntent.ToggleBookmark -> toggleBookmark(intent.id)
        }
    }

}
