package org.breera.project.book.presentation

import org.breera.project.book.domain.Book

sealed interface BookListAction {
    data class OnSearchQueryChange(val query: String) : BookListAction
    data class OnBookClick(val book: Book) : BookListAction
    data class OnTabSelected(val tab: Int) : BookListAction
}
