package com.sample.app.bookexplorer.data.repository

import com.sample.app.bookexplorer.data.remote.BookApi
import com.sample.app.bookexplorer.data.remote.dto.toBookData
import com.sample.app.bookexplorer.domain.model.BookData
import com.sample.app.bookexplorer.domain.repository.BookRepository
import javax.inject.Inject

class BookRepositoryImpl @Inject constructor(
    private val api: BookApi,
) : BookRepository {

    override suspend fun getBooksResults(bookTitle: String): List<BookData> {
        return api.getBookResult(bookTitle).toBookData()
    }
}
