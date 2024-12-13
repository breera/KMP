package org.breera.project.book.presentation.book_detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import composemultiplaformsample.composeapp.generated.resources.Res
import composemultiplaformsample.composeapp.generated.resources.description_unavailable
import composemultiplaformsample.composeapp.generated.resources.languages
import composemultiplaformsample.composeapp.generated.resources.pages
import composemultiplaformsample.composeapp.generated.resources.rating
import composemultiplaformsample.composeapp.generated.resources.synopsis
import org.breera.project.book.presentation.book_detail.components.BlurredImage
import org.breera.project.book.presentation.book_detail.components.BookDetailViewModel
import org.breera.project.book.presentation.book_detail.components.Chips
import org.jetbrains.compose.resources.stringResource
import kotlin.math.round

@Composable
fun BookDetailScreenRoot(bookDetailViewModel: BookDetailViewModel, onBackClick: () -> Unit) {
    MaterialTheme {
        val state by bookDetailViewModel.bookDetailState.collectAsStateWithLifecycle()
        BookDetailScreen(state) {
            when (it) {
                BookDetailAction.OnBackClick -> onBackClick()
                else -> Unit
            }
            bookDetailViewModel.onAction(it)
        }
    }
}

@Composable
fun BookDetailScreen(
    state: BookDetailState,
    onAction: (BookDetailAction) -> Unit
) {
    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BlurredImage(
            url = state.book?.imageUrl,
            isFavourite = state.isFavourite,
            onFavouriteClick = {
                onAction.invoke(BookDetailAction.OnFavouriteClick)
            },
            onBackClick = {
                onAction.invoke(BookDetailAction.OnBackClick)
            },
            content = {
                Column(
                    modifier = Modifier.fillMaxHeight()
                        .padding(10.dp)
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = state.book?.title ?: "",
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = state.book?.authors?.joinToString() ?: "",
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Row(
                        Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        state.book?.averageRating?.let {
                            Chips(
                                title = stringResource(Res.string.rating),
                                chipTexts = listOf(round(it).toString())
                            )
                        }
                        state.book?.numPages?.let {
                            Chips(
                                title = stringResource(Res.string.pages),
                                chipTexts = listOf(it.toString())
                            )
                        }
                    }

                    state.book?.languages?.let {
                        Chips(
                            title = stringResource(Res.string.languages),
                            chipTexts = it
                        )
                    }
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = stringResource(Res.string.synopsis),
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.headlineLarge,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        if (state.isLoading) {
                            CircularProgressIndicator()
                        } else {
                            Text(
                                text = state.book?.description
                                    ?: stringResource(Res.string.description_unavailable),
                                textAlign = TextAlign.Justify,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }
            }
        )
    }
}
