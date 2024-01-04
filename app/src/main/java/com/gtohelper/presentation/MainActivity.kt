package com.gtohelper.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import androidx.navigation.compose.rememberNavController
import com.gtohelper.presentation.navigation.AppNavHost
import com.gtohelper.presentation.ui.theme.AppBackgroundColor
import com.gtohelper.presentation.ui.theme.GTOHelperTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GTOHelperTheme {
                Surface(
                    color = AppBackgroundColor,
                ) {
                    val navController = rememberNavController()
                    AppNavHost(navController)
                }
            }
        }
    }
}