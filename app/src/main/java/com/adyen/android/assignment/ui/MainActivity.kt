package com.adyen.android.assignment.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import com.adyen.android.assignment.ui.navigation.AppNavHost
import com.adyen.android.assignment.ui.theme.APODTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            APODApp()
        }
    }
}

@Composable
fun APODApp() {
    APODTheme {
        val navController = rememberNavController()
        AppNavHost(
            navController = navController
        )
    }
}