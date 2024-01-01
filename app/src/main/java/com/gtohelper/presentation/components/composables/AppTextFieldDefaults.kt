package com.gtohelper.presentation.components.composables

import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

object AppTextFieldDefaults {

    @Composable
    fun colors(): TextFieldColors {
        return TextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            errorIndicatorColor = Color.Transparent,
            disabledSupportingTextColor = Color.Transparent,
            errorSupportingTextColor = Color.Transparent,
            focusedSupportingTextColor = Color.Transparent,
            unfocusedSupportingTextColor = Color.Transparent,
        )
    }
}