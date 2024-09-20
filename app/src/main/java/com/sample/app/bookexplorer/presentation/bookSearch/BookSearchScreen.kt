package com.sample.app.bookexplorer.presentation.bookSearch

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sample.app.bookexplorer.R
import com.sample.app.bookexplorer.domain.model.BookData
import com.sample.app.bookexplorer.presentation.bookSearch.components.BookItem
import com.sample.app.bookexplorer.presentation.bookSearch.components.EmptyState
import com.sample.app.bookexplorer.presentation.bookSearch.components.SearchBox
import com.sample.app.bookexplorer.presentation.bookSearch.data.BookSearchIntent
import com.sample.app.bookexplorer.presentation.bookSearch.data.BookSearchState
import com.sample.app.bookexplorer.presentation.bookSearch.data.SortOption
import com.sample.app.bookexplorer.presentation.common.UiEvent

@Composable
fun BookSearchScreen(
    modifier: Modifier = Modifier,
    viewModel: BookSearchScreenViewModel = hiltViewModel(),
    onNavigate: (UiEvent.Navigate<BookData>) -> Unit,
) {
    val state = viewModel.tasksState
    val listState = rememberLazyListState()

    // To restore scroll position when navigating to another screen
    LaunchedEffect(key1 = state.bookList) {
        listState.scrollToItem(
            state.listState.firstVisibleItemIndex,
            state.listState.firstVisibleItemScrollOffset
        )
    }

    // To save scroll position when navigating to another screen
    DisposableEffect(Unit) {
        onDispose {
            viewModel.saveScrollPosition(
                listState
            )
        }
    }

    BookSearchScreen(
        modifier = modifier,
        bookSearchState = state,
        listState = listState,
        onSearchQueryChanged = ({ newValue ->
            viewModel.onEvent(BookSearchIntent.UpdateQueryChanged(newValue))

        }),
        onSearchAction = {
            viewModel.onEvent(BookSearchIntent.SearchQueryChanged(state.searchQuery))
        },
        onNavigate = onNavigate,
        onSortOption = { sort ->
            viewModel.onEvent(BookSearchIntent.SortChanged(sort))
            viewModel.onEvent(BookSearchIntent.ScrollToTop)
        })

}

@Composable
fun BookSearchScreen(
    modifier: Modifier = Modifier,
    bookSearchState: BookSearchState,
    listState: LazyListState,
    onSearchQueryChanged: (TextFieldValue) -> Unit,
    onSearchAction: () -> Unit,
    onNavigate: (UiEvent.Navigate<BookData>) -> Unit,
    onSortOption: (sortOption: SortOption) -> Unit,
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = modifier
    ) {
        SearchBox(
            bookSearchState = bookSearchState,
            onSearchQueryChanged = onSearchQueryChanged,
            onSearchAction = onSearchAction,
            keyboardController = keyboardController,
            onSortOption = onSortOption,
        )
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            when {
                bookSearchState.isLoading -> {
                    CircularProgressIndicator(modifier = Modifier.testTag("loading_indicator"))
                }

                bookSearchState.error.isNotEmpty() -> {
                    Text(text = stringResource(R.string.error_getting_books))
                }

                bookSearchState.emptyState -> {
                    Column(modifier = Modifier.fillMaxSize()) {
                        EmptyState(modifier = Modifier.testTag("empty_state"))
                    }
                }

                else -> {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = 4.dp),
                        state = listState,
                    ) {
                        items(bookSearchState.bookList, key = { it.id }) { item ->
                            BookItem(
                                title = item.title,
                                publishDate = item.firstPublishYear ?: "",
                                authorName = item.authorName,
                                bookImage = item.bookImageLarge,
                            ) {
                                onNavigate(UiEvent.Navigate(item))
                            }
                        }
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun BookSearchScreenPreview(modifier: Modifier = Modifier) {
    BookSearchScreen(
        bookSearchState = BookSearchState(),
        listState = LazyListState(),
        onSearchQueryChanged = {},
        onSearchAction = {},
        onNavigate = {},
        onSortOption = {}
    )
}
