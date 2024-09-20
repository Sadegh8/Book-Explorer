package com.sample.app.bookexplorer.presentation.bookSearch.data

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.ui.text.input.TextFieldValue
import com.sample.app.bookexplorer.domain.model.BookData

data class BookSearchState(
    val isLoading: Boolean = false,
    val bookList: List<BookData> = emptyList(),
    val error: String = "",
    val emptyState: Boolean = false,
    val searchQuery: TextFieldValue = TextFieldValue(""),
    val listState: LazyListState = LazyListState(),
)


sealed class BookSearchIntent {
    data class SearchQueryChanged(val query: TextFieldValue) : BookSearchIntent()
    data class UpdateQueryChanged(val query: TextFieldValue) : BookSearchIntent()
    data class SortChanged(val sortOption: SortOption) : BookSearchIntent()
    data object ScrollToTop : BookSearchIntent()
}
