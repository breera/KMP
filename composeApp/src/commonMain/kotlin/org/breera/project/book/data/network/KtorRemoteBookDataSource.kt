package org.breera.project.book.data.network

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import org.breera.project.book.data.dtos.SearchResponseDto
import org.breera.project.core.data.safeCall
import org.breera.project.core.domain.DataError
import org.breera.project.core.domain.Result

class KtorRemoteBookDataSource(private val httpClient: HttpClient) : RemoteBookDataSource {

    override suspend fun getSearchedBook(
        query: String,
        searchLimit: String
    ): Result<SearchResponseDto, DataError.Remote> {
        return safeCall {
            httpClient.get("https://openlibrary.org/search.json?q=$query&limit=$searchLimit")
        }
    }
}