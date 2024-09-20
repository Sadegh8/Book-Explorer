package com.sample.app.bookexplorer.presentation.bookSearch.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.sample.app.bookexplorer.R
import com.sample.app.bookexplorer.presentation.bookSearch.data.SortOption

@Composable
fun SortIconButton(
    onSortSelected: (SortOption) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    IconButton(
        onClick = { expanded = true },
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Icon(
            modifier = Modifier.size(30.dp),
            imageVector = ImageVector.vectorResource(id = R.drawable.sort_ic),
            contentDescription = "Sort Items"
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                text = { Text("Date Descending") },
                onClick = {
                    expanded = false
                    onSortSelected(SortOption.DateDescending)
                }
            )
            DropdownMenuItem(
                text = { Text("Date Ascending") },
                onClick = {
                    expanded = false
                    onSortSelected(SortOption.DateAscending)
                }
            )
            DropdownMenuItem(
                text = { Text("Author Name") },
                onClick = {
                    expanded = false
                    onSortSelected(SortOption.AuthorName)
                }
            )
        }
    }
}
