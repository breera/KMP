package org.breera.project.book.data.dtos

import kotlinx.serialization.Serializable

/**
 * Created by Breera Hanif on 13/12/2024.
 */

@Serializable(with = BookWorkDtoSerializer::class)
data class BookWorkDto(
    val description: String? = null
)