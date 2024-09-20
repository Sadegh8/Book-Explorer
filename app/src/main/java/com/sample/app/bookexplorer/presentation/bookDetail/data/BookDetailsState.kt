package com.sample.app.bookexplorer.presentation.bookDetail.data

import com.sample.app.bookexplorer.domain.model.BookData

data class BookDetailsState(
    val isLoading: Boolean = false,
    val book: BookData? = null,
    val error: String = "",
)