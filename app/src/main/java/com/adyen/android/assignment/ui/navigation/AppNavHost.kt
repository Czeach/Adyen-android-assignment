package com.adyen.android.assignment.ui.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.adyen.android.assignment.ui.screens.APODDetailScreen
import com.adyen.android.assignment.ui.screens.list.APODListScreen
import com.adyen.android.assignment.ui.screens.list.APODListViewModel

@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screens.APODListScreen.route
    ) {
        composable(
            route = Screens.APODListScreen.route
        ) {
            val viewModel = hiltViewModel<APODListViewModel>()
            APODListScreen(
                viewModel = viewModel,
            )
        }
        composable(
            route = Screens.APODDetailScreen.route
        ) {
            APODDetailScreen()
        }
    }
}