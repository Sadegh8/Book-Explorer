package com.sample.app.bookexplorer.presentation.bookDetail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.toRoute
import com.sample.app.bookexplorer.domain.model.BookData
import com.sample.app.bookexplorer.presentation.bookDetail.data.BookDetailsState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BookDetailScreenViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    var detailState by mutableStateOf(BookDetailsState())
        private set

    init {
        detailState = detailState.copy(isLoading = true)
        savedStateHandle.toRoute<BookData>().let {
            detailState = detailState.copy(isLoading = false, book = it)
        }
    }
}
