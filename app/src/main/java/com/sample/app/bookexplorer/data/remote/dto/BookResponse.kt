package com.sample.app.bookexplorer.data.remote.dto

import com.google.gson.annotations.SerializedName
import com.sample.app.bookexplorer.data.common.Constants.IMAGE_BASE_URL
import com.sample.app.bookexplorer.domain.model.BookData

data class BookResponse(
    val numFound: Int,
    val start: Int,
    val numFoundExact: Boolean,
    val docs: List<Book>
)

data class Book(
    @SerializedName("key") val id: String,
    @SerializedName("title") val title: String,
    @SerializedName("author_name") val authorName: List<String>?,
    @SerializedName("first_publish_year") val firstPublishYear: Int?,
    @SerializedName("cover_i") val coverId: Int?,
    @SerializedName("isbn") val isbn: List<String>?,
    @SerializedName("first_sentence") val firstSentence: List<String>?,
    @SerializedName("number_of_pages_median") val numberOfPagesMedian: Int?,
)

fun BookResponse.toBookData(): List<BookData> {
    return this.docs.map {
        val isbn = it.isbn?.firstOrNull()
        var bookImageSmall = ""
        var bookImageLarge = ""
        if (!isbn.isNullOrBlank()) {
            bookImageSmall = "$IMAGE_BASE_URL$isbn-S.jpg"
            bookImageLarge = "$IMAGE_BASE_URL$isbn-L.jpg"
        }

        BookData(
            id = it.id,
            title = it.title,
            authorName = it.authorName?.firstOrNull() ?: "",
            firstPublishYear = it.firstPublishYear?.toString(),
            coverId = it.coverId,
            bookImageSmall = bookImageSmall,
            bookImageLarge = bookImageLarge,
            firstSentence = it.firstSentence?.firstOrNull() ?: "",
            pageCount = it.numberOfPagesMedian.toString(),
        )
    }
}
