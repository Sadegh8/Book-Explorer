package com.sample.app.bookexplorer.presentation.bookSearch

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.ui.text.input.TextFieldValue
import com.google.common.truth.Truth.assertThat
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.sample.app.bookexplorer.domain.model.BookData
import com.sample.app.bookexplorer.presentation.bookSearch.data.BookSearchState
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BookSearchScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testLoadingState_isDisplayed() {
        composeTestRule.setContent {
            BookSearchScreen(
                bookSearchState = BookSearchState(isLoading = true),
                listState = LazyListState(),
                onSearchQueryChanged = {},
                onSearchAction = {},
                onNavigate = {},
                onSortOption = {}
            )
        }

        composeTestRule.onNode(hasTestTag("loading_indicator"))
            .assertIsDisplayed()
    }

    @Test
    fun testErrorState_isDisplayed() {
        // Set the screen to error state
        val bookSearchState = BookSearchState(error = "Something went wrong")

        composeTestRule.setContent {
            BookSearchScreen(
                bookSearchState = bookSearchState,
                listState = LazyListState(),
                onSearchQueryChanged = {},
                onSearchAction = {},
                onNavigate = {},
                onSortOption = {}
            )
        }

        // Assert that error message is displayed
        composeTestRule.onNodeWithText("Error getting books").assertIsDisplayed()
    }

    @Test
    fun testEmptyState_isDisplayed() {
        composeTestRule.setContent {
            BookSearchScreen(
                bookSearchState = BookSearchState(emptyState = true),
                listState = LazyListState(),
                onSearchQueryChanged = {},
                onSearchAction = {},
                onNavigate = {},
                onSortOption = {}
            )
        }

        composeTestRule.onNode(hasTestTag("empty_state"))
            .assertIsDisplayed()
    }

    @Test
    fun testBookList_isDisplayed() {
        // Create a mock list of books
        val books = listOf(
            BookData(
                id = "1",
                title = "Book One",
                authorName = "Author One",
                firstPublishYear = "2000",
                coverId = 1,
                bookImageLarge = "large_image_url_1",
                bookImageSmall = "small_image_url_1",
                firstSentence = "First sentence of Book One.",
                pageCount = "300"
            ),
            BookData(
                id = "2",
                title = "Book Two",
                authorName = "Author Two",
                firstPublishYear = "2010",
                coverId = 2,
                bookImageLarge = "large_image_url_2",
                bookImageSmall = "small_image_url_2",
                firstSentence = "First sentence of Book Two.",
                pageCount = "250"
            )
        )

        val bookSearchState = BookSearchState(bookList = books)

        composeTestRule.setContent {
            BookSearchScreen(
                bookSearchState = bookSearchState,
                listState = LazyListState(),
                onSearchQueryChanged = {},
                onSearchAction = {},
                onNavigate = {},
                onSortOption = {}
            )
        }

        // Assert that the books are displayed in the list
        composeTestRule.onNodeWithText("Book One").assertIsDisplayed()
        composeTestRule.onNodeWithText("Book Two").assertIsDisplayed()
    }

    @Test
    fun testSearchBoxFunctionality() {
        val searchQuery = mutableStateOf(TextFieldValue(""))

        composeTestRule.setContent {
            BookSearchScreen(
                bookSearchState = BookSearchState(searchQuery = searchQuery.value),
                listState = LazyListState(),
                onSearchQueryChanged = { newValue ->
                    searchQuery.value = newValue
                },
                onSearchAction = {},
                onNavigate = {},
                onSortOption = {}
            )
        }

        composeTestRule.onNodeWithTag("search_box")
            .performClick()

        composeTestRule.onNodeWithTag("search_box")
            .performTextInput("Search Query")

        // Verify that the text  updated
        assertThat(searchQuery.value.text).isEqualTo("Search Query")
    }
}
