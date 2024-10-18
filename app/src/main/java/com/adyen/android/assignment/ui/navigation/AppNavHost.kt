package com.adyen.android.assignment.ui.navigation

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.adyen.android.assignment.ui.screens.APODDetailScreen
import com.adyen.android.assignment.ui.screens.APODListScreen
import com.adyen.android.assignment.ui.viewmodel.APODViewModel

@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screens.APODListScreen.route
    ) {
        fun onBackPressed() {
            if (navController.previousBackStackEntry != null) navController.navigateUp()
        }

        composable(
            route = Screens.APODListScreen.route
        ) {
            val viewModel = hiltViewModel<APODViewModel>()
            APODListScreen(
                viewModel = viewModel,
                onAPODClicked = { title, date, explanation, url ->
                    navController.navigate(
                        Screens.APODDetailScreen.route + "/" +
                                Uri.encode(title) + "/" +
                                Uri.encode(date) + "/" +
                                Uri.encode(explanation) + "/" +
                                Uri.encode(url)
                    )
                }
            )
        }
        composable(
            route = Screens.APODDetailScreen.route + "/{title}" + "/{date}" + "/{explanation}" + "/{url}",
            arguments = listOf(
                navArgument("title") {
                    type = NavType.StringType
                },
                navArgument("date") {
                    type = NavType.StringType
                },
                navArgument("explanation") {
                    type = NavType.StringType
                },
                navArgument("url") {
                    type = NavType.StringType
                }
            )
        ) { navBackStackEntry ->

            val backStackEntry = remember(navBackStackEntry) {
                navController.getBackStackEntry(
                    Screens.APODDetailScreen.route + "/{title}" + "/{date}" + "/{explanation}" + "/{url}"
                )
            }

            val viewModel = hiltViewModel<APODViewModel>(backStackEntry)
            APODDetailScreen(
                viewModel = viewModel,
                onBackPressed = {
                    onBackPressed()
                }
            )
        }
    }
}