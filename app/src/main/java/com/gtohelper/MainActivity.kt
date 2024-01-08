package com.gtohelper

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import com.gtohelper.presentation.ui.GHApp
import com.gtohelper.presentation.ui.theme.GTOHelperTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GTOHelperTheme {
                Surface {
                    GHApp()
                }
            }
        }
    }
}