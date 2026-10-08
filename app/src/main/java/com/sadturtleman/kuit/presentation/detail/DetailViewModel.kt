package com.sadturtleman.kuit.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sadturtleman.kuit.domain.GetBookUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException
import java.net.UnknownHostException

// id 는 생성자가 아니라 Intent 로 받는다. 로드 시점은 화면의 LaunchedEffect(bookId) 가 정한다.
class DetailViewModel(
    private val getBook: GetBookUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(DetailUiState())
    val state = _state.asStateFlow()

    fun onIntent(intent: DetailIntent) {
        when (intent) {
            is DetailIntent.Load -> load(intent.id)
        }
    }

    private fun load(id: String) = viewModelScope.launch {
        _state.update { it.copy(isLoading = true, error = null) }
        runCatching { getBook(id) }
            .onSuccess { book ->
                _state.update {
                    if (book == null) it.copy(isLoading = false, error = "책 정보를 찾을 수 없어요")
                    else it.copy(isLoading = false, book = book)
                }
            }
            .onFailure { e -> _state.update { it.copy(isLoading = false, error = e.toUserMessage()) } }
    }

    private fun Throwable.toUserMessage(): String = when (this) {
        is UnknownHostException -> "인터넷 연결을 확인하세요"
        is IOException -> "네트워크가 불안정해요. 잠시 후 다시 시도하세요"
        else -> "책 정보를 불러오지 못했어요"
    }
}
