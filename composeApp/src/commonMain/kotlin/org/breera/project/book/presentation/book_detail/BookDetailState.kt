package org.breera.project.book.presentation.book_detail

import org.breera.project.book.domain.Book

data class BookDetailState(
    val isLoading: Boolean = true,
    val book: Book? = null,
    val isFavourite: Boolean = false
)
