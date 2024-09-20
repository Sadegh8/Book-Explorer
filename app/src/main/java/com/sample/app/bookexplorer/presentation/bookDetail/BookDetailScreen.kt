package com.sample.app.bookexplorer.presentation.bookDetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.sample.app.bookexplorer.domain.model.BookData
import com.sample.app.bookexplorer.presentation.bookDetail.data.BookDetailsState

@Composable
fun BookDetailScreen(
    modifier: Modifier = Modifier,
    viewModel: BookDetailScreenViewModel = hiltViewModel(),
    book: BookData,
) {

    BookDetailScreen(modifier = modifier, state = viewModel.detailState)

}


@Composable
fun BookDetailScreen(modifier: Modifier = Modifier, state: BookDetailsState) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = state.book?.bookImageLarge,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth(0.6f)
                .aspectRatio(0.75f)
                .clip(RoundedCornerShape(8.dp))
                .shadow(8.dp, RoundedCornerShape(8.dp)),
            onError = {
                // Handle image loading error
            }
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = state.book?.title ?: "Unknown Title",
            style = MaterialTheme.typography.headlineMedium.copy(fontSize = 24.sp),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = state.book?.authorName ?: "Unknown Author",
            style = MaterialTheme.typography.bodyLarge.copy(fontSize = 18.sp, color = MaterialTheme.colorScheme.primary),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Published: ${state.book?.firstPublishYear ?: "-"}",
            style = MaterialTheme.typography.bodyMedium.copy(fontSize = 16.sp),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(12.dp))

        if (!state.book?.pageCount.isNullOrBlank()) {
            Text(
                text = "Pages: ${state.book?.pageCount}",
                style = MaterialTheme.typography.bodyMedium.copy(fontSize = 16.sp),
                textAlign = TextAlign.Center
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        if (!state.book?.firstSentence.isNullOrBlank()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(
                        Brush.linearGradient(
                            listOf(
                                MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
                                MaterialTheme.colorScheme.primary.copy(alpha = 0.05f) // Little transparent color
                            )
                        )
                    )
                    .padding(16.dp)
            ) {
                Text(
                    text = "First sentence:\n${state.book?.firstSentence}",
                    style = MaterialTheme.typography.bodyMedium.copy(fontSize = 16.sp),
                    textAlign = TextAlign.Start
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun BookDetailScreenPreview(modifier: Modifier = Modifier) {
    BookDetailScreen(state = BookDetailsState())
}