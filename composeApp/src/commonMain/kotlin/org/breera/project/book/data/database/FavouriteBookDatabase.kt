package org.breera.project.book.data.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

/**
 * Created by Breera Hanif on 13/12/2024.
 */

@Database(
    entities = [BookEntity::class],
    version = 1
)

@TypeConverters(StringListDataConverter::class)
@ConstructedBy(BookDatabaseConstructor::class)
abstract class FavouriteBookDatabase : RoomDatabase() {
    abstract val dao: BookDatabaseDao

    companion object {
        const val DB_NAME = "book.db"
    }
}
