package org.breera.project.book.data.repository

import androidx.sqlite.SQLiteException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.breera.project.book.data.database.BookDatabaseDao
import org.breera.project.book.data.network.RemoteBookDataSource
import org.breera.project.book.data.network.mapper.toBook
import org.breera.project.book.data.network.mapper.toBookEntity
import org.breera.project.book.domain.Book
import org.breera.project.book.domain.BookRepository
import org.breera.project.core.domain.DataError
import org.breera.project.core.domain.EmptyResult
import org.breera.project.core.domain.Result
import org.breera.project.core.domain.map

class DefaultBookRepository(
    private val remoteBookDataSource: RemoteBookDataSource,
    private val dao: BookDatabaseDao
) :
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
        val localResult = dao.getFavouriteBooks(bookId)
        return if (localResult == null) {
            remoteBookDataSource
                .fetchDescription(bookId)
                .map { it.description }
        } else {
            Result.Success(localResult.description)
        }

    }

    override fun getFavouriteBook(): Flow<List<Book>> {
        return dao
            .getFavouriteBooks()
            .map { entries ->
                entries.map { it.toBook() }
            }
    }

    override fun isBookFavourite(id: String): Flow<Boolean> {
        return dao
            .getFavouriteBooks()
            .map { entries ->
                entries.any { it.id == id }
            }
    }

    override suspend fun markAsFavourite(book: Book): EmptyResult<DataError.Local> {
        return try {
            dao.upsert(book.toBookEntity())
            Result.Success(Unit)
        } catch (e: SQLiteException) {
            Result.Error(DataError.Local.DISK_FULL)
        }
    }

    override suspend fun deleteFromFavourite(id: String) {
        return dao
            .deleteBookById(id)
    }
}
