package com.sample.app.bookexplorer.presentation.navigation

import com.sample.app.bookexplorer.domain.model.BookData
import kotlinx.serialization.Serializable

@Serializable
object BookSearch

@Serializable
data class Details(
    val book: BookData,
)
