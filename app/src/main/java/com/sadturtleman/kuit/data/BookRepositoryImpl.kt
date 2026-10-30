package com.sadturtleman.kuit.data

import com.sadturtleman.kuit.domain.Book
import com.sadturtleman.kuit.domain.BookRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import javax.inject.Inject

// [TODO 3-b] Hilt 가 이 클래스를 만들 수 있도록 생성자에 @Inject 를 붙인다.
//            BookService 는 NetworkModule 의 @Provides 에서 온다.
class BookRepositoryImpl @Inject constructor(
    private val service: BookService,
) : BookRepository {

    private val bookmarkedIds = MutableStateFlow<Set<String>>(emptySet())
    private var lastQuery: String ?= null;
    private var lastFetched: List<VolumeDto> = emptyList()   // 북마크 화면용 캐시

    // 4주차 미션 B — 검색어는 밖에서 받는다
    override fun searchBooks(query: String): Flow<List<Book>>{
        val network: Flow<List<VolumeDto>> = flow {
            if (query != lastQuery){
                lastFetched = service.searchBooks(query).items.orEmpty()
                lastQuery = query
            }
            emit(lastFetched)
        }

        return combine(network, bookmarkedIds) { dtos, ids ->
            dtos.map { it.toBook(isBookmarked = it.id in ids) }
        }
    }

    // 4주차 상태: 실패를 null 로 바꾼다. (5주차 미션 B 에서 고친다)
    override suspend fun getBook(id: String): Book =
        service.getBook(id).toBook(isBookmarked = id in bookmarkedIds.value)

    override fun toggleBookmark(id: String) {
        bookmarkedIds.update { ids -> if (id in ids) ids - id else ids + id }
    }

    override fun getBookmarkedBooks(): Flow<List<Book>> =
        bookmarkedIds.map { ids ->
            lastFetched.filter { it.id in ids }.map { it.toBook(isBookmarked = true) }
        }
}
