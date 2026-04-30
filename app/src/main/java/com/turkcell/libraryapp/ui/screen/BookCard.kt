package com.turkcell.libraryapp.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.turkcell.libraryapp.data.model.Book


@Composable
fun BookCard(
    book: Book,
    onDeleteClick: () -> Unit,
    onEditClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Text(text = book.title, style = MaterialTheme.typography.titleMedium)
            Text(text = "Yazar: ${book.author}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Kategori: ${book.category}", style = MaterialTheme.typography.bodySmall)
            Text(text = "Sayfa: ${book.pageCount}", style = MaterialTheme.typography.bodySmall)
            Text(text = "Mevcut: ${book.avaiableCopies}/${book.totalCopies}", style = MaterialTheme.typography.bodySmall)

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(onClick = onEditClick) {
                    Icon(Icons.Default.Edit, contentDescription = "Düzenle")
                }
                IconButton(onClick = onDeleteClick) {
                    Icon(Icons.Default.Delete, contentDescription = "Sil")
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun BookCardPreview() {
    BookCard(
        book = Book(
            id = "1",
            title = "Suç ve Ceza",
            author = "Fyodor Dostoyevski",
            isbn = "978-605-1234-56-7",
            category = "Roman",
            pageCount = 687,
            totalCopies = 5,
            avaiableCopies = 3
        ),
        onDeleteClick = {},
        onEditClick = {}
    )
}