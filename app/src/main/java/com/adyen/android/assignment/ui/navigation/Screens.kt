package com.adyen.android.assignment.ui.navigation

sealed class Screens(val route: String) {
    data object APODListScreen : Screens("list_screen")
    data object APODDetailScreen : Screens("details_screen")
}