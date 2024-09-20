package com.sample.app.bookexplorer.presentation.bookSearch

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sample.app.bookexplorer.data.common.Resource
import com.sample.app.bookexplorer.domain.useCases.GetBooksUseCase
import com.sample.app.bookexplorer.presentation.bookSearch.data.BookSearchIntent
import com.sample.app.bookexplorer.presentation.bookSearch.data.BookSearchState
import com.sample.app.bookexplorer.presentation.bookSearch.data.SortOption
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookSearchScreenViewModel @Inject constructor(
    private val useCase: GetBooksUseCase,
) : ViewModel() {

    var tasksState by mutableStateOf(BookSearchState())
        private set

    /**
     * Handle intents
     */
    fun onEvent(intent: BookSearchIntent) {
        when (intent) {
            is BookSearchIntent.SearchQueryChanged -> {
                findBook(intent.query)
            }

            is BookSearchIntent.UpdateQueryChanged -> {
                tasksState = tasksState.copy(searchQuery = intent.query)

            }

            is BookSearchIntent.SortChanged -> {
                handleSorting(intent)
            }

            BookSearchIntent.ScrollToTop -> {
                tasksState = tasksState.copy(listState = LazyListState(0, 0))
            }
        }
    }


    /**
     * Find Book by Query and Update State
     */
    private fun findBook(query: TextFieldValue) {
        viewModelScope.launch {
            useCase.invoke(query.text).collect { result ->
                tasksState = when (result) {
                    is Resource.Success -> {
                        val books = result.data ?: emptyList()

                        when {
                            books.isEmpty() -> {
                                BookSearchState(
                                    emptyState = true,
                                )
                            }

                            else -> {
                                BookSearchState(
                                    bookList = books,
                                    emptyState = false,
                                    searchQuery = query
                                )
                            }
                        }
                    }

                    is Resource.Error -> {
                        BookSearchState(error = result.message ?: "An unexpected error occurred")
                    }

                    is Resource.Loading -> {
                        tasksState.copy(isLoading = true)
                    }
                }
            }
        }
    }

    /**
     * Handle Sorting
     */
    private fun handleSorting(intent: BookSearchIntent.SortChanged) {
        tasksState = when (intent.sortOption) {
            SortOption.DateDescending -> tasksState.copy(
                bookList = tasksState.bookList.sortedByDescending { it.firstPublishYear }
            )

            SortOption.DateAscending -> tasksState.copy(
                bookList = tasksState.bookList.sortedBy { it.firstPublishYear }
            )

            SortOption.AuthorName -> tasksState.copy(
                bookList = tasksState.bookList.sortedBy { it.authorName }
            )
        }
    }

    fun saveScrollPosition(lazyListState: LazyListState) {
        tasksState = tasksState.copy(
            listState = lazyListState,
        )
    }
}
