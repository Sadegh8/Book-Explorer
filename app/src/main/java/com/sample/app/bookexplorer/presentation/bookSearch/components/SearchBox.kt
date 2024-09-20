package com.sample.app.bookexplorer.presentation.bookSearch.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sample.app.bookexplorer.presentation.bookSearch.data.BookSearchState
import com.sample.app.bookexplorer.presentation.bookSearch.data.SortOption
import com.sample.app.bookexplorer.ui.theme.LightBlue
import com.sample.app.bookexplorer.ui.theme.LightGray1

@Composable
fun SearchBox(
    modifier: Modifier = Modifier,
    bookSearchState: BookSearchState,
    onSearchQueryChanged: (TextFieldValue) -> Unit,
    onSearchAction: () -> Unit,
    keyboardController: SoftwareKeyboardController?,
    onSortOption: (sortOption: SortOption) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(0.86f)
                .defaultMinSize(minHeight = 65.dp)
                .padding(
                    horizontal = 16.dp
                )
                .padding(top = 10.dp)
                .clip(MaterialTheme.shapes.medium)
                .background(LightBlue.copy(alpha = 0.7f))
        ) {
            BasicTextField(
                value = bookSearchState.searchQuery,
                onValueChange = onSearchQueryChanged,
                modifier = Modifier
                    .fillMaxWidth()
                    .focusable()
                    .testTag("search_box"),
                textStyle = TextStyle(fontSize = 18.sp),
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        onSearchAction()
                        keyboardController?.hide()
                    }
                ),
                decorationBox = { innerTextField ->
                    Box(modifier = Modifier.padding(16.dp)) {
                        if (bookSearchState.searchQuery.text.isEmpty()) {
                            Text(
                                text = "Search...",
                                style = TextStyle(color = LightGray1, fontSize = 18.sp)
                            )
                        }
                        innerTextField()
                    }
                }
            )
        }

        // Sort
        AnimatedVisibility(
            visible = bookSearchState.bookList.isNotEmpty(),
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            SortIconButton {
                onSortOption(it)
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SearchBoxPreview() {
    SearchBox(
        bookSearchState = BookSearchState(),
        onSearchQueryChanged = {},
        onSearchAction = {},
        keyboardController = LocalSoftwareKeyboardController.current
    ) {

    }
}
