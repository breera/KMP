package org.breera.project.book.presentation.book_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import composemultiplaformsample.composeapp.generated.resources.Res
import composemultiplaformsample.composeapp.generated.resources.favourite
import composemultiplaformsample.composeapp.generated.resources.no_favourite_results
import composemultiplaformsample.composeapp.generated.resources.no_serach_results
import composemultiplaformsample.composeapp.generated.resources.search_results
import org.breera.project.book.domain.Book
import org.breera.project.book.presentation.BookListAction
import org.breera.project.book.presentation.BookListState
import org.breera.project.book.presentation.book_list.composables.BookList
import org.breera.project.book.presentation.book_list.composables.BookSearchBar
import org.breera.project.core.presentation.DarkBlue
import org.breera.project.core.presentation.DesertWhite
import org.breera.project.core.presentation.SandYellow
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun BookListRoot(
    viewModel: BookListViewModel = koinViewModel(),
    onBookClick: (Book) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    BookListScreen(state) {
        when (it) {
            is BookListAction.OnBookClick -> onBookClick(it.book)
            else -> Unit
        }
        viewModel.onAction(it)
    }
}

@Composable
fun BookListScreen(state: BookListState, onAction: (BookListAction) -> Unit) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val pagerState = rememberPagerState { 2 }
    val searchResultlazyState = rememberLazyListState()
    val favouritelazyState = rememberLazyListState()

    LaunchedEffect(state.searchResult) {
        searchResultlazyState.animateScrollToItem(0)
    }
    LaunchedEffect(state.selectedTab) {
        pagerState.animateScrollToPage(state.selectedTab)
     }
    LaunchedEffect(pagerState.currentPage){
            onAction(BookListAction.OnTabSelected(pagerState.currentPage))
    }

    MaterialTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(DarkBlue)
                .statusBarsPadding(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            BookSearchBar(
                query = state.searchQuery,
                onQueryChange = {
                    onAction(BookListAction.OnSearchQueryChange(it))
                },
                onItemSearch = {
                    keyboardController?.hide()
                },
                modifier = Modifier.widthIn(400.dp)
                    .fillMaxWidth().padding(16.dp)
            )
            Surface(
                Modifier.weight(1f).fillMaxWidth(),
                color = DesertWhite,
                shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    TabRow(
                        selectedTabIndex = state.selectedTab,
                        modifier = Modifier.padding(vertical = 12.dp).widthIn(700.dp)
                            .fillMaxWidth(),
                        containerColor = DesertWhite,
                        contentColor = SandYellow,
                        indicator = {
                            TabRowDefaults.SecondaryIndicator(
                                color = SandYellow,
                                modifier = Modifier.tabIndicatorOffset(
                                    currentTabPosition = it[state.selectedTab]
                                )
                            )
                        }
                    ) {
                        Tab(
                            selected = state.selectedTab == 0,
                            onClick = {
                                onAction(
                                    BookListAction.OnTabSelected(0)
                                )
                            },
                            modifier = Modifier.weight(1f),
                            selectedContentColor = SandYellow,
                            unselectedContentColor = Color.Black.copy(alpha = 0.5f)
                        ) {
                            Text(
                                stringResource(Res.string.search_results),
                                modifier = Modifier.padding(vertical = 12.dp)
                            )
                        }
                        Tab(
                            selected = state.selectedTab == 1,
                            onClick = {
                                onAction(
                                    BookListAction.OnTabSelected(1)
                                )
                            },
                            modifier = Modifier.weight(1f),
                            selectedContentColor = SandYellow,
                            unselectedContentColor = Color.Black.copy(alpha = 0.5f)
                        ) {
                            Text(
                                stringResource(Res.string.favourite),
                                modifier = Modifier.padding(vertical = 12.dp)
                            )
                        }
                    }

                    Spacer(Modifier.height(4.dp))
                    HorizontalPager(
                        state = pagerState,
                        modifier = Modifier.fillMaxWidth()
                            .weight(1f)
                    ) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {

                            when (it) {
                                0 -> {
                                    if (state.isLoading) {
                                        CircularProgressIndicator()
                                    } else {
                                        when {
                                            (state.errorMessage != null) -> {
                                                Text(
                                                    state.errorMessage.asString(),
                                                    textAlign = TextAlign.Center,
                                                    style = MaterialTheme.typography.headlineSmall,
                                                    color = MaterialTheme.colorScheme.error
                                                )
                                            }

                                            state.searchResult.isEmpty() -> {
                                                Text(
                                                    stringResource(Res.string.no_serach_results),
                                                    textAlign = TextAlign.Center,
                                                    style = MaterialTheme.typography.headlineSmall
                                                )
                                            }

                                            else -> {
                                                BookList(
                                                    books = state.searchResult,
                                                    onBookClick = {
                                                        onAction(BookListAction.OnBookClick(it))
                                                    },
                                                    modifier = Modifier.fillMaxSize(),
                                                    scrollState = searchResultlazyState

                                                )
                                            }
                                        }
                                    }
                                }

                                1 -> {
                                    when {
                                        state.favouriteBooks.isEmpty() -> {
                                            Text(
                                                stringResource(Res.string.no_favourite_results),
                                                textAlign = TextAlign.Center,
                                                style = MaterialTheme.typography.headlineSmall
                                            )
                                        }

                                        else-> {
                                            BookList(
                                                books = state.favouriteBooks,
                                                onBookClick = {
                                                    onAction(BookListAction.OnBookClick(it))
                                                },
                                                modifier = Modifier.fillMaxSize(),
                                                scrollState = favouritelazyState

                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

            }
        }
    }
}