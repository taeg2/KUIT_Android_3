package com.sadturtleman.kuit

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.sadturtleman.kuit.data.BookRepositoryImpl
import com.sadturtleman.kuit.domain.repository.BookRepository
import com.sadturtleman.kuit.ui.bookmarks.BookmarksScreen
import com.sadturtleman.kuit.ui.bookmarks.BookmarksViewModel
import com.sadturtleman.kuit.ui.detail.DetailScreen
import com.sadturtleman.kuit.ui.detail.DetailViewModel
import com.sadturtleman.kuit.ui.home.HomeScreen
import com.sadturtleman.kuit.ui.home.HomeViewModel
import kotlinx.serialization.Serializable

@Serializable
data object Home : NavKey

@Serializable
data class Detail(val id: Int) : NavKey

@Serializable
data object Bookmarks : NavKey

@Composable
fun App() {
    val backStack = rememberNavBackStack(Home)

    val bookRepository = BookRepositoryImpl()

    NavDisplay(
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        entryProvider = entryProvider {
            entry<Home> {
                val vm: HomeViewModel = viewModel {
                    HomeViewModel(
                        bookRepository = bookRepository
                    )
                }
                val state by vm.state.collectAsStateWithLifecycle()

                HomeScreen(
                    state = state,
                    onRefresh = { vm.load() },
                    onBookClick = { id -> backStack.add(Detail(id)) },
                    onBookmarkClick = { id -> vm.toggleBookmark(id) },
                    onBookmarksClick = { backStack.add(Bookmarks) }
                )
            }

            entry<Detail> { key ->
                // 책마다 다른 ViewModel이 필요하니 key 지정
                val vm: DetailViewModel = viewModel(key = "detail-${key.id}") {
                    DetailViewModel(bookId = key.id, bookRepository = bookRepository)
                }
                val state by vm.state.collectAsStateWithLifecycle()

                DetailScreen(
                    state = state,
                    onBack = { backStack.removeLastOrNull() },
                    onBookmarkClick = { vm.toggleBookmark() }
                )
            }

            entry<Bookmarks> {
                val vm: BookmarksViewModel = viewModel {
                    BookmarksViewModel(bookRepository = bookRepository)
                }
                val state by vm.state.collectAsStateWithLifecycle()

                BookmarksScreen(
                    state = state,
                    onBack = { backStack.removeLastOrNull() },
                    onBookClick = { id ->
                        backStack.add(Detail(id))
                        Log.d("BackStack", backStack.toList().toString())
                    },
                    onBookmarkClick = { id -> vm.toggleBookmark(id) }
                )
            }
        }
    )
}