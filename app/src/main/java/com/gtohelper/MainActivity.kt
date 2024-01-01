package com.gtohelper

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.gtohelper.theme.AppBackgroundColor
import com.gtohelper.theme.GTOHelperTheme
import com.gtohelper.theme.LocalSpacing
import com.gtohelper.theme.spacing
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
                    AppHavHost(navController)
                }
            }
        }
    }
}