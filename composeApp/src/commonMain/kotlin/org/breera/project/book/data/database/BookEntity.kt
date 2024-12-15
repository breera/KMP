package org.breera.project.book.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by Breera Hanif on 13/12/2024.
 */
@Entity
data class BookEntity(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val title: String,
    val imageUrl: String,
    val authors: List<String>,
    val description: String?,
    val languages: List<String>,
    val firstPublishYear: String?,
    val averageRating: Double?,
    val ratingCount: Int?,
    val numPages: Int?,
    val numEditions: Int
)
