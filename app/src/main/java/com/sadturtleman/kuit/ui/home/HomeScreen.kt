package com.sadturtleman.kuit.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sadturtleman.kuit.R
import com.sadturtleman.kuit.data.BookRepositoryImpl
import com.sadturtleman.kuit.domain.model.Book
import com.sadturtleman.kuit.domain.repository.BookRepository
import com.sadturtleman.kuit.ui.BookRow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    state: HomeState,
    onRefresh: () -> Unit,
    onBookClick: (Int) -> Unit,
    onBookmarkClick: (Int) -> Unit,
    onBookmarksClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("KUIT Books") }, actions = {
                TextButton(
                    onClick = onRefresh
                ) {
                    Text(
                        "새로고침"
                    )
                }
            })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onBookmarksClick) {
                Text(
                    "★ 북마크 ${state.bookmarkCount}",
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
        }

    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
        ) {

            when {
                state.isLoading -> CircularProgressIndicator(Modifier.align(Alignment.Center))
                state.error != null -> Text("에러")
                else -> {
                    LazyColumn(
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        items(state.books, key = { it.id }) { book ->
                            BookRow(
                                book = book,
                                onClick = { onBookClick(book.id) },
                                onBookmarkClick = { onBookmarkClick(book.id) }
                            )
                        }
                    }
                }
            }
        }
    }
}



@Preview
@Composable
private fun BookRowPrev() {
    BookRow(
        book = Book(1, "객지프", "이것저것", false), onClick = {}, onBookmarkClick = {})
}

@Preview
@Composable
private fun HomeScreenPrev() {
    HomeScreen(
        state = HomeState(
            books = listOf<Book>(
                Book(1, "test1", "test1"),
                Book(2, "test2", "test2"),
                Book(3, "test3", "test3")
            )
        ),
        onRefresh = {},
        onBookClick = {},
        onBookmarkClick = {},
        onBookmarksClick = { }
    )
}