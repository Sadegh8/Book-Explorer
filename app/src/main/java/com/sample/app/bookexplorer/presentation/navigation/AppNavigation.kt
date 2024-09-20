package com.sample.app.bookexplorer.presentation.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.sample.app.bookexplorer.domain.model.BookData
import com.sample.app.bookexplorer.presentation.bookDetail.BookDetailScreen
import com.sample.app.bookexplorer.presentation.bookSearch.BookSearchScreen

@ExperimentalAnimationApi
@Composable
internal fun AppNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    padding: PaddingValues = PaddingValues(0.dp),
) {
    NavHost(
        navController = navController,
        startDestination = BookSearch,
        modifier = modifier,
    ) {
        addBookSearch(
            padding = padding,
            navController = navController,
        )
        addDetails(
            padding = padding,
        )
    }
}


@ExperimentalAnimationApi
private fun NavGraphBuilder.addBookSearch(
    padding: PaddingValues,
    navController: NavHostController,
) {
    composable<BookSearch>
    {
        BookSearchScreen(modifier = Modifier.padding(padding)) {
            navController.navigate(it.route)
        }
    }
}

@ExperimentalAnimationApi
private fun NavGraphBuilder.addDetails(
    padding: PaddingValues,
) {
    composable<BookData> {
        val args = it.toRoute<BookData>()
        BookDetailScreen(modifier = Modifier.padding(padding), book = args)
    }
}
