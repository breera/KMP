package org.breera.project.book.data.database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

/**
 * Created by Breera Hanif on 13/12/2024.
 */
@Dao
interface BookDatabaseDao {

    @Upsert
    suspend fun upsert(book: BookEntity)

    @Query("SELECT * FROM BookEntity")
    fun getFavouriteBooks(): Flow<List<BookEntity>>

    @Query("SELECT * FROM BookEntity WHERE id = :id")
    suspend fun getFavouriteBooks(id: String): BookEntity?

    @Query("DELETE FROM BookEntity WHERE id = :id")
    suspend fun deleteBookById(id: String)
}
