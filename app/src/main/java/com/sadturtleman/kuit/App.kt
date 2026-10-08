package com.sadturtleman.kuit

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.sadturtleman.kuit.data.BookRepositoryImpl
import com.sadturtleman.kuit.data.Network
import com.sadturtleman.kuit.domain.GetBookUseCase
import com.sadturtleman.kuit.domain.GetBooksUseCase
import com.sadturtleman.kuit.presentation.detail.DetailScreen
import com.sadturtleman.kuit.presentation.detail.DetailViewModel
import com.sadturtleman.kuit.presentation.home.HomeScreen
import com.sadturtleman.kuit.presentation.home.HomeViewModel
import kotlinx.serialization.Serializable

@Serializable data object Home : NavKey
@Serializable data class Detail(val id: String) : NavKey

@Composable
fun App() {
    val backStack = rememberNavBackStack(Home)

    NavDisplay(
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        entryProvider = entryProvider {
            entry<Home> {
                val vm: HomeViewModel = viewModel {
                    // 3주차: FakeBookRepository()  →  4주차: 진짜 서버
                    HomeViewModel(GetBooksUseCase(BookRepositoryImpl(Network.bookApi)))
                }
                val state by vm.state.collectAsStateWithLifecycle()
                HomeScreen(
                    state = state,
                    onIntent = vm::onIntent,
                    onBookClick = { id -> backStack.add(Detail(id)) },
                )
            }
            entry<Detail> { key ->
                val vm: DetailViewModel = viewModel {
                    DetailViewModel(GetBookUseCase(BookRepositoryImpl(Network.bookApi)))
                }
                val state by vm.state.collectAsStateWithLifecycle()
                DetailScreen(
                    bookId = key.id,
                    state = state,
                    onIntent = vm::onIntent,
                    onBack = { backStack.removeLastOrNull() },
                )
            }
        }
    )
}
