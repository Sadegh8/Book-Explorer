package com.sample.app.bookexplorer.data.remote

import com.sample.app.bookexplorer.data.remote.dto.BookResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface BookApi {

    @GET("/search.json")
    suspend fun getBookResult(@Query("title") title: String): BookResponse
}
