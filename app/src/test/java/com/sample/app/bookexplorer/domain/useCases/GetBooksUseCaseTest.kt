package com.sample.app.bookexplorer.domain.useCases

import android.util.Log
import com.google.common.truth.Truth.assertThat
import com.sample.app.bookexplorer.data.common.Resource
import com.sample.app.bookexplorer.domain.model.BookData
import com.sample.app.bookexplorer.domain.repository.BookRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import java.io.IOException

class GetBooksUseCaseTest {

    private lateinit var getBooksUseCase: GetBooksUseCase
    private lateinit var bookRepository: BookRepository

    @Before
    fun setUp() {
        bookRepository = mockk()
        mockkStatic(Log::class)
        every { Log.e(any(), any()) } returns 0

        MockKAnnotations.init(this)
        getBooksUseCase = GetBooksUseCase(bookRepository)
    }

    @Test
    fun `invoke should return loading and success when data is retrieved`() = runTest {
        // Arrange
        val books = listOf(
            BookData(
                id = "1",
                title = "Test Book",
                authorName = "Author",
                firstPublishYear = "2021",
                coverId = null,
                pageCount = "200",
                firstSentence = null,
                bookImageSmall = "",
                bookImageLarge = ""
            )
        )
        coEvery { bookRepository.getBooksResults("test") } returns books

        // Act
        val result = getBooksUseCase("test").toList()

        // Assert
        assertThat(result[0]).isInstanceOf(Resource.Loading::class.java)
        assertThat(result[1]).isInstanceOf(Resource.Success::class.java)
        assertThat((result[1] as Resource.Success).data).isEqualTo(books)
    }

    @Test
    fun `invoke should return error when no books are found`() = runTest {
        // Arrange
        coEvery { bookRepository.getBooksResults("test") } returns emptyList()

        // Act
        val result = getBooksUseCase("test").toList()

        // Assert
        assertThat(result[0]).isInstanceOf(Resource.Loading::class.java)
        assertThat(result[1]).isInstanceOf(Resource.Error::class.java)
        assertThat((result[1] as Resource.Error).message).isEqualTo("No books found with the given title.")
    }

    @Test
    fun `invoke should return error on HttpException`() = runTest {
        // Arrange
        val exception = mockk<HttpException>()
        coEvery { bookRepository.getBooksResults("test") } throws exception
        every { exception.localizedMessage } returns "HTTP error occurred"

        // Act
        val flow = getBooksUseCase("test")

        // Assert
        val result = flow.toList()
        assertThat(result[0]).isInstanceOf(Resource.Loading::class.java)
        assertThat(result[1]).isInstanceOf(Resource.Error::class.java)
        assertThat((result[1] as Resource.Error).message).isEqualTo("HTTP error occurred")
    }

    @Test
    fun `invoke should return error on IOException`() = runTest {
        // Mock the repository to throw IOException
        coEvery { bookRepository.getBooksResults(any()) } throws IOException()

        val result = getBooksUseCase("someTitle").toList()

        // Assert that the last emit is Resource.Error
        assertThat(result[0]).isInstanceOf(Resource.Loading::class.java)
        assertThat(result[1]).isInstanceOf(Resource.Error::class.java)
        assertThat((result[1] as Resource.Error).message).isEqualTo("Couldn't reach server. Check your internet connection.")
    }

    @Test
    fun `invoke should return error on generic exception`() = runTest {
        // Arrange
        coEvery { bookRepository.getBooksResults("test") } throws Exception("Unexpected error")

        // Act
        val result = getBooksUseCase("test").toList()

        // Assert
        assertThat(result[0]).isInstanceOf(Resource.Loading::class.java)
        assertThat(result[1]).isInstanceOf(Resource.Error::class.java)
        assertThat((result[1] as Resource.Error).message).isEqualTo("Unexpected error")
    }
}
