package org.breera.project.book.data.database

import androidx.room.RoomDatabase

/**
 * Created by Breera Hanif on 13/12/2024.
 */
expect class DatabaseFactory {
    fun create(): RoomDatabase.Builder<FavouriteBookDatabase>
}
