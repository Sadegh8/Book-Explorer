package com.sample.app.bookexplorer.presentation.bookSearch.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun EmptyState(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "There's no book...")
    }

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun EmptyStatePreview(modifier: Modifier = Modifier) {
    EmptyState()
}
