@file:OptIn(FlowPreview::class)

package org.breera.project.book.presentation.book_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.breera.project.book.data.repository.DefaultBookRepository
import org.breera.project.book.domain.Book
import org.breera.project.book.presentation.BookListAction
import org.breera.project.book.presentation.BookListState
import org.breera.project.core.domain.onError
import org.breera.project.core.domain.onSuccess
import org.breera.project.core.presentation.toUiText

class BookListViewModel(private val bookRepository: DefaultBookRepository) : ViewModel() {
    private val _state = MutableStateFlow(BookListState())
    private val cachedBooks = emptyList<Book>()
    private val searchJob: Job? = null
    val state = _state.onStart {
        if (cachedBooks.isEmpty()) {
            observeSearchQuery()
        }
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        _state.value
    )

    fun onAction(action: BookListAction) {
        when (action) {
            is BookListAction.OnBookClick -> {}
            is BookListAction.OnSearchQueryChange -> {
                _state.update {
                    it.copy(searchQuery = action.query)
                }
            }

            is BookListAction.OnTabSelected -> {
                _state.update {
                    it.copy(selectedTab = action.tab)
                }
            }
        }
    }

    private suspend fun observeSearchQuery() {
        state.map { it.searchQuery }
            .distinctUntilChanged() // don't call api if query is same
            .debounce(500L) // only search if stops typing after this time
            .onEach {
                when {
                    it.isBlank() -> {
                        _state.update {
                            it.copy(
                                errorMessage = null,
                                searchResult = cachedBooks
                            )
                        }
                    }

                    it.length > 2 -> {
                        searchJob?.cancel()
                        searchBooks(it)
                    }
                }
            }.launchIn(viewModelScope)
    }

    private suspend fun searchBooks(querry: String) =
        viewModelScope.launch {
            _state.update {
                it.copy(isLoading = true)
            }
            bookRepository.searchBooks(
                querry, "100"
            )
                .onSuccess { searchResult ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = null,
                            searchResult = searchResult
                        )
                    }
                }
                .onError { error ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = error.toUiText(),
                            searchResult = emptyList()
                        )
                    }
                }
        }
}

