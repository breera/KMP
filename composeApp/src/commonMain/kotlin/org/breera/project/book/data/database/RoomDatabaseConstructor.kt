@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package org.breera.project.book.data.database

import androidx.room.RoomDatabaseConstructor

/**
 * Created by Breera Hanif on 13/12/2024.
 */

@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object BookDatabaseConstructor : RoomDatabaseConstructor<FavouriteBookDatabase> {
    override fun initialize(): FavouriteBookDatabase
}
