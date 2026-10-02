package com.sadturtleman.kuit.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.sadturtleman.kuit.R
import com.sadturtleman.kuit.domain.model.Book

@Composable
fun BookRow(
    book: Book,
    onClick: () -> Unit,
    onBookmarkClick: () -> Unit
) {
    Card(
        Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(book.title)
                Text(book.author)
            }
            Image(
                painter = painterResource(
                    if (book.isBookmarked) R.drawable.ic_fullstar else R.drawable.ic_emptystar
                ),
                contentDescription = null,
                modifier = Modifier.clickable(onClick = onBookmarkClick)
            )
        }

    }
}