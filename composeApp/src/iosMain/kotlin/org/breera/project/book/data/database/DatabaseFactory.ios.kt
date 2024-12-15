@file:OptIn(ExperimentalForeignApi::class, ExperimentalForeignApi::class)

package org.breera.project.book.data.database

import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask

/**
 * Created by Breera Hanif on 13/12/2024.
 */

actual class DatabaseFactory {
    actual fun create(): RoomDatabase.Builder<FavouriteBookDatabase> {
        val dbFile = documentDirectory() + "/${FavouriteBookDatabase.DB_NAME}"
        return Room.databaseBuilder<FavouriteBookDatabase>(
            name = dbFile
        )
    }

    private fun documentDirectory(): String {
        val documentDirectory = NSFileManager.defaultManager.URLForDirectory(
            directory = NSDocumentDirectory,
            inDomain = NSUserDomainMask,
            appropriateForURL = null,
            create = false,
            error = null
        )
        return requireNotNull(documentDirectory?.path)
    }
}
