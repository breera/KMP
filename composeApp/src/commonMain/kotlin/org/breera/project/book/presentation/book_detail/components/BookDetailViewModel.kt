package org.breera.project.book.presentation.book_detail.components

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.breera.project.app.Route
import org.breera.project.book.data.repository.DefaultBookRepository
import org.breera.project.book.presentation.book_detail.BookDetailAction
import org.breera.project.book.presentation.book_detail.BookDetailState
import org.breera.project.core.domain.onSuccess

/**
 * Created by Breera Hanif on 10/12/2024.
 */
class BookDetailViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val bookRepository: DefaultBookRepository
) : ViewModel() {
    private val _bookDetailState = MutableStateFlow(BookDetailState())
    val bookDetailState = _bookDetailState.onStart {
        getDescription()
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000L),
        _bookDetailState.value
    )

    fun onAction(action: BookDetailAction) {
        when (action) {
            BookDetailAction.OnFavouriteClick -> {}
            is BookDetailAction.OnSelectedBookChange -> {
                _bookDetailState.update {
                    it.copy(book = action.book)
                }
            }

            BookDetailAction.OnBackClick -> {}
        }
    }

    private fun getDescription() {
        val id = savedStateHandle.toRoute<Route.BookDetails>().bookId
        viewModelScope.launch {
            _bookDetailState.update {
                it.copy(isLoading = true)
            }
            bookRepository.fetchDescription(
                id
            ).onSuccess { description ->
                _bookDetailState.update {
                    it.copy(
                        isLoading = false,
                        book = it.book?.copy(description = description)
                    )
                }
            }
        }
    }
}