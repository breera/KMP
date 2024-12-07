package org.breera.project

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import org.breera.project.book.domain.Book
import org.breera.project.book.presentation.BookListState
import org.breera.project.book.presentation.book_list.BookListScreen
import org.breera.project.book.presentation.book_list.composables.BookSearchBar

@Preview(showBackground = true)
@Composable
fun compasablepreviews() {
    MaterialTheme {
        BookSearchBar(
            query = "porta",
            onQueryChange = {},
            onItemSearch = {},
            modifier = Modifier
        )
    }
}

@Preview
@Composable
fun BookListScreenPreview() {
        BookListScreen(state = BookListState(
            searchQuery = "persius",
            searchResult = emptyList()
        ),
            onAction = {}
        )
}


