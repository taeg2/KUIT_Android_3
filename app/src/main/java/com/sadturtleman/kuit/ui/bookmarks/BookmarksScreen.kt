package com.sadturtleman.kuit.ui.bookmarks

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.sadturtleman.kuit.R
import com.sadturtleman.kuit.ui.BookRow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookmarksScreen(
    state: BookmarksUiState,
    onBack: () -> Unit,
    onBookClick: (Int) -> Unit,
    onBookmarkClick: (Int) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("북마크") },
                navigationIcon = {
                    Image(
                        painter = painterResource(R.drawable.ic_left),
                        contentDescription = null,
                        modifier = Modifier.clickable(onClick = onBack)
                    )
                }
            )
        },

        ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
        ) {

            when {
                state.isLoading -> CircularProgressIndicator(Modifier.align(Alignment.Center))
                state.error != null -> Text("에러")
                state.isEmpty -> EmptyBookmarks(Modifier.align(Alignment.Center))
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

@Composable
private fun EmptyBookmarks(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Image(
            painter = painterResource(R.drawable.ic_emptystar),
            contentDescription = null,
            modifier = Modifier.size(56.dp)
        )
        Text("아직 북마크한 책이 없어요")
        Text("목록에서 ★ 를 눌러 저장해 보세요" )
    }
}