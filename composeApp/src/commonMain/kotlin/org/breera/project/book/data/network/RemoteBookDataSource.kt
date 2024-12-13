package org.breera.project.book.data.network

import org.breera.project.book.data.dtos.BookWorkDto
import org.breera.project.book.data.dtos.SearchResponseDto
import org.breera.project.core.domain.DataError
import org.breera.project.core.domain.Result

interface RemoteBookDataSource {
    suspend fun getSearchedBook(
        query: String,
        searchLimit: String
    ): Result<SearchResponseDto, DataError.Remote>

    suspend fun fetchDescription(
        bookId: String
    ): Result<BookWorkDto, DataError.Remote>

}