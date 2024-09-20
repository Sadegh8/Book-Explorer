package com.sample.app.bookexplorer.domain.repository

import com.sample.app.bookexplorer.domain.model.BookData

interface BookRepository {
    suspend fun getBooksResults(bookTitle: String): List<BookData>
}
