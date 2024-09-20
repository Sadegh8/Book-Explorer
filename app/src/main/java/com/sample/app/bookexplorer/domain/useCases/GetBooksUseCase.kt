package com.sample.app.bookexplorer.domain.useCases

import android.util.Log
import com.sample.app.bookexplorer.data.common.Resource
import com.sample.app.bookexplorer.domain.model.BookData
import com.sample.app.bookexplorer.domain.repository.BookRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetBooksUseCase @Inject constructor(
    private val bookRepository: BookRepository
) {
    operator fun invoke(bookTitle: String): Flow<Resource<List<BookData>>> = flow {
        emit(Resource.Loading())

        try {
            val bookResults = bookRepository.getBooksResults(bookTitle)
                .filter {
                    it.firstPublishYear.isNullOrBlank().not() && it.authorName.isNotBlank()
                }

            if (bookResults.isNotEmpty()) {
                emit(Resource.Success(bookResults))
            } else {
                emit(Resource.Error("No books found with the given title."))
            }
        } catch (e: HttpException) {
            val errorMessage = e.localizedMessage ?: "An unexpected error occurred!"
            Log.e("GetBooksUseCase", errorMessage)
            emit(Resource.Error(errorMessage))
        } catch (e: IOException) {
            val errorMessage = "Couldn't reach server. Check your internet connection."
            Log.e("GetBooksUseCase", errorMessage)
            emit(Resource.Error(errorMessage))
        } catch (e: Exception) {
            val errorMessage = e.localizedMessage ?: "Unknown error occurred."
            Log.e("GetBooksUseCase", errorMessage)
            emit(Resource.Error(errorMessage))
        }
    }
}
