package org.breera.project

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import org.breera.project.book.presentation.BookListState
import org.breera.project.book.presentation.book_detail.components.Chips
import org.breera.project.book.presentation.book_list.BookListScreen
import org.breera.project.book.presentation.book_list.composables.BookSearchBar

@Preview(showBackground = true)
@Composable
fun ComposablePreviews() {
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

@Preview(showBackground = true)
@Composable
fun ChipsPreview() {
    MaterialTheme {
        Chips("rating", listOf("4.0", "4.0"), true)
    }
}

@Preview(showBackground = true)
@Composable
fun BookDetailScreenPreview() {
    MaterialTheme {
        // BookDetailScreen(BookDetailState())
    }
}
