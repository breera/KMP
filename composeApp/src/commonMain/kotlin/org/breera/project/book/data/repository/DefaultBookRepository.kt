package org.breera.project.book.data.repository

import org.breera.project.book.data.network.RemoteBookDataSource
import org.breera.project.book.data.network.mapper.toBook
import org.breera.project.book.domain.Book
import org.breera.project.book.domain.BookRepository
import org.breera.project.core.domain.DataError
import org.breera.project.core.domain.Result
import org.breera.project.core.domain.map

class DefaultBookRepository(private val remoteBookDataSource: RemoteBookDataSource) :
    BookRepository {
    override suspend fun searchBooks(
        query: String,
        searchLimit: String
    ): Result<List<Book>, DataError.Remote> {
        return remoteBookDataSource
            .getSearchedBook(query, searchLimit)
            .map { dto ->
                dto.results.map {
                    it.toBook()
                }
            }
    }

    override suspend fun fetchDescription(
        bookId: String
    ): Result<String?, DataError.Remote> {
        return remoteBookDataSource
            .fetchDescription(bookId)
            .map { it.description }
    }
}