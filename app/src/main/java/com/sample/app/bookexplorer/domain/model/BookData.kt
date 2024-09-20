package com.sample.app.bookexplorer.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class BookData(
    val id: String,
    val title: String,
    val authorName: String,
    val firstPublishYear: String?,
    val coverId: Int?,
    val bookImageSmall: String?,
    val bookImageLarge: String?,
    val firstSentence: String?,
    val pageCount: String?,
)
