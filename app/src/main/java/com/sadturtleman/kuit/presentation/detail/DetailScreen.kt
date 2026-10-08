package com.sadturtleman.kuit.presentation.detail

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sadturtleman.kuit.domain.Book

// 화면은 state 와 onIntent (+ 뒤로가기 콜백) 만 받는다. Repository 도 backStack 도 모른다.
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    bookId: String,
    state: DetailUiState,
    onIntent: (DetailIntent) -> Unit,
    onBack: () -> Unit,
) {
    // key(bookId) 가 바뀔 때마다 다시 로드
    LaunchedEffect(bookId) { onIntent(DetailIntent.Load(bookId)) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("책 상세") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Text("←", style = MaterialTheme.typography.headlineSmall)
                    }
                },
            )
        }
    ) { padding ->
        Box(Modifier.padding(padding).fillMaxSize()) {
            when {
                state.isLoading -> CircularProgressIndicator(Modifier.align(Alignment.Center))
                state.error != null -> Column(
                    Modifier.align(Alignment.Center).padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(state.error, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.titleMedium)
                    Spacer(Modifier.height(20.dp))
                    Button(onClick = { onIntent(DetailIntent.Load(bookId)) }) { Text("다시 시도") }
                }
                state.book != null -> BookDetail(state.book)
            }
        }
    }
}

@Composable
private fun BookDetail(book: Book) {
    Column(Modifier.padding(24.dp)) {
        Text(book.title, style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(8.dp))
        Text(book.author, style = MaterialTheme.typography.bodyLarge)

        // pageCount · year (둘 다 없는 책도 있다)
        val meta = listOfNotNull(book.pages?.let { "${it}p" }, book.year).joinToString(" · ")
        if (meta.isNotEmpty()) {
            Spacer(Modifier.height(12.dp))
            Text(meta, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }

        book.description?.let { description ->
            Spacer(Modifier.height(24.dp))
            Text("소개", style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(8.dp))
            Text(
                if (description.length > 200) description.take(200) + "…" else description,
                style = MaterialTheme.typography.bodyMedium,
            )
        }
    }
}
