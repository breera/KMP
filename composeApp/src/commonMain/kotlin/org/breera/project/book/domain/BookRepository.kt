package org.breera.project.book.domain

import kotlinx.coroutines.flow.Flow
import org.breera.project.core.domain.DataError
import org.breera.project.core.domain.EmptyResult
import org.breera.project.core.domain.Result

interface BookRepository {
    suspend fun searchBooks(
        query: String,
        searchLimit: String
    ): Result<List<Book>, DataError.Remote>

    suspend fun fetchDescription(
        bookId: String
    ): Result<String?, DataError.Remote>

    fun getFavouriteBook(): Flow<List<Book>>

    fun isBookFavourite(id: String): Flow<Boolean>

    suspend fun markAsFavourite(book: Book): EmptyResult<DataError.Local>

    suspend fun deleteFromFavourite(id: String)

}
