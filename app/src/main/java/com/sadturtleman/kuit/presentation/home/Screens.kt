package com.sadturtleman.kuit.presentation.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sadturtleman.kuit.domain.Book

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    state: HomeUiState,
    onIntent: (HomeIntent) -> Unit,
    onBookClick: (String) -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("KUIT Books") },
                actions = {
                    TextButton(onClick = { onIntent(HomeIntent.Refresh) }) { Text("새로고침") }
                },
            )
        }
    ) { padding ->
        Column(Modifier.padding(padding).fillMaxSize()) {
            SearchBar(
                initialQuery = state.query,
                onSearch = { text -> onIntent(HomeIntent.Search(text)) },
            )
            Box(Modifier.weight(1f).fillMaxWidth()) {
                when {
                    state.isLoading -> CircularProgressIndicator(Modifier.align(Alignment.Center))
                    state.error != null -> ErrorView(
                        message = state.error,
                        onRetry = { onIntent(HomeIntent.Refresh) },   // 새 Intent 없이 Refresh 재사용
                        modifier = Modifier.align(Alignment.Center),
                    )
                    else -> LazyColumn(
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                    ) {
                        items(state.books, key = { it.id }) { book ->
                            BookRow(book = book, onClick = { onBookClick(book.id) })
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ErrorView(message: String, onRetry: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier.padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text("✈️", style = MaterialTheme.typography.displayMedium)
        Spacer(Modifier.height(12.dp))
        Text(message, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.titleMedium)
        Spacer(Modifier.height(20.dp))
        Button(onClick = onRetry) { Text("다시 시도") }
    }
}

// 입력 중인 글자는 화면이 remember 로 들고, 검색 버튼을 눌렀을 때만 Intent 로 올린다.
@Composable
private fun SearchBar(initialQuery: String, onSearch: (String) -> Unit) {
    var text by remember { mutableStateOf(initialQuery) }
    Row(
        Modifier.fillMaxWidth().padding(start = 16.dp, end = 16.dp, top = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        OutlinedTextField(
            value = text,
            onValueChange = { text = it },
            modifier = Modifier.weight(1f),
            singleLine = true,
        )
        Spacer(Modifier.width(8.dp))
        Button(onClick = { onSearch(text) }) { Text("검색") }
    }
}

@Composable
private fun BookRow(book: Book, onClick: () -> Unit) {
    Card(Modifier.fillMaxWidth().clickable(onClick = onClick)) {
        Column(Modifier.padding(16.dp)) {
            Text(book.title, style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(4.dp))
            Text(book.author, style = MaterialTheme.typography.bodyMedium)
        }
    }
}
