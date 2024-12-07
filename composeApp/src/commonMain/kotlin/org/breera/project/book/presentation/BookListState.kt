package org.breera.project.book.presentation

import org.breera.project.book.domain.Book
import org.breera.project.core.presentation.UiText

data class BookListState(
    val searchQuery: String = "Kotlin",
    val searchResult: List<Book> = emptyList(),
    val favouriteBooks: List<Book> = emptyList(),
    val isLoading: Boolean = false,
    val selectedTab: Int = 0,
    val errorMessage: UiText? = null
)

private val books = (1..100).map {
    Book(
        id = it.toString(),
        title = "Book $it",
        imageUrl = "https://duckduckgo.com/?q=decore",
        authors = listOf("ABC", "XYZ"),
        description = "Description $it",
        languages = listOf(),
        firstPublishYear = null,
        averageRating = 4.5656,
        ratingCount = 5,
        numPages = 100,
        numEditions = 9707
    )
}
