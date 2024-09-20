package com.sample.app.bookexplorer.presentation.common

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.sample.app.bookexplorer.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    modifier: Modifier = Modifier,
    navController: NavController,
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val showBackButton = rememberSaveable { (mutableStateOf(true)) }
    LaunchedEffect(navBackStackEntry) {
        showBackButton.value = navController.previousBackStackEntry != null
    }

    TopAppBar(
        modifier = modifier,
        title =
        {
            Text(
                text = stringResource(R.string.app_name),
                textAlign = TextAlign.Center,
            )

        },
        navigationIcon = {
            if (showBackButton.value) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Close"
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(),
    )
}
