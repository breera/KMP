package org.breera.project.book.data.database

import androidx.room.TypeConverter
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

/**
 * Created by Breera Hanif on 13/12/2024.
 */
object StringListDataConverter {
    @TypeConverter
    fun fromList(list: List<String>): String {
        return Json.encodeToString(list)
    }

    @TypeConverter
    fun fromString(value: String): List<String> {
        return Json.decodeFromString(value)
    }
}
