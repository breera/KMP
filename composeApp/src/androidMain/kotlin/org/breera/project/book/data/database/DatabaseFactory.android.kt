package org.breera.project.book.data.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * Created by Breera Hanif on 13/12/2024.
 */
actual class DatabaseFactory(
    private val context: Context
) {
    actual fun create(): RoomDatabase.Builder<FavouriteBookDatabase> {
        val appContext = context.applicationContext
        val dbFile = appContext.getDatabasePath(FavouriteBookDatabase.DB_NAME)
        return Room.databaseBuilder(
            context = context,
            name = dbFile.absolutePath
        )
    }
}