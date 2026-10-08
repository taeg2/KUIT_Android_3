package com.sadturtleman.kuit.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sadturtleman.kuit.domain.GetBooksUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException
import java.net.UnknownHostException

// 3주차 정답 그대로. 서버가 붙어도 이 파일은 한 글자도 안 바뀐다.
class HomeViewModel(
    private val getBooks: GetBooksUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(HomeUiState())
    val state = _state.asStateFlow()

    init { onIntent(HomeIntent.Refresh) }

    fun onIntent(intent: HomeIntent) {
        when (intent) {
            HomeIntent.Refresh -> load(_state.value.query)
            is HomeIntent.Search -> load(intent.query.trim())
        }
    }

    private fun load(query: String) = viewModelScope.launch {
        _state.update { it.copy(isLoading = true, error = null, query = query) }
        // 검색어가 없으면 전체(기본) 목록. Google Books 는 q 가 필수라 빈 값으로는 요청할 수 없다
        runCatching { getBooks(query.ifEmpty { DEFAULT_QUERY }) }
            .onSuccess { books -> _state.update { it.copy(isLoading = false, books = books) } }
            .onFailure { e -> _state.update { it.copy(isLoading = false, error = e.toUserMessage()) } }
    }

    // 예외 → 사람이 읽을 문구. 화면은 문구만 보여 주고 예외 종류는 모른다.
    private fun Throwable.toUserMessage(): String = when (this) {
        is UnknownHostException -> "인터넷 연결을 확인하세요"
        is IOException -> "네트워크가 불안정해요. 잠시 후 다시 시도하세요"
        else -> "책 목록을 불러오지 못했어요"
    }

    private companion object {
        const val DEFAULT_QUERY = "android"
    }
}
