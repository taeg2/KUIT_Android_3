package com.sadturtleman.kuit.ui.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sadturtleman.kuit.R
import com.sadturtleman.kuit.domain.model.Book

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    state: DetailUiState,
    onBack: () -> Unit,
    onBookmarkClick: () -> Unit
) {
    Scaffold(topBar = {
        TopAppBar(
            title = { Text("책 상세") },
            navigationIcon = {
                Image(
                    painter = painterResource(R.drawable.ic_left),
                    contentDescription = null,
                    modifier = Modifier.clickable(onClick = onBack)
                )
            }
        )
    }) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            when {
                state.isLoading -> CircularProgressIndicator(Modifier.align(Alignment.Center))
                state.error != null -> Text(state.error, Modifier.align(Alignment.Center))
                state.book != null -> BookDetail(state.book, onBookmarkClick)
            }
        }
    }
}

@Composable
private fun BookDetail(book: Book, onBookmarkClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(book.title)
        Text(book.author)
        Text(
            "${book.pages}p · ${book.year}",
        )
        Button(onClick = onBookmarkClick) {
            Text(if (book.isBookmarked) "★ 북마크 해제" else "☆ 북마크에 추가")
        }
        Text("소개")
        Text(book.description)
    }
}