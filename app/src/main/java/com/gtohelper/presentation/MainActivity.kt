package com.gtohelper.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.navigation.compose.rememberNavController
import com.gtohelper.presentation.navigation.AppNavHost
import com.gtohelper.presentation.ui.theme.BackgroundLight
import com.gtohelper.presentation.ui.theme.GTOHelperTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GTOHelperTheme {
                Surface(
                    color = BackgroundLight,
                ) {
                    val navController = rememberNavController()
                    AppNavHost(navController)
                }
            }
        }
    }
}