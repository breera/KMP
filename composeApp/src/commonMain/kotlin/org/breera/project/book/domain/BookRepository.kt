package org.breera.project.book.domain

import org.breera.project.core.domain.DataError
import org.breera.project.core.domain.Result

interface BookRepository {
    suspend fun searchBooks(
        query: String,
        searchLimit: String
    ): Result<List<Book>, DataError.Remote>
}

