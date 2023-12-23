package com.gtohelper.presentation.components

import androidx.compose.foundation.background
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.gtohelper.R

@Composable
fun TransparentAddFab(
    onClick: () -> Unit,
    contentDescription: String?,
) {
    IconButton(
        modifier = Modifier.background(Color.Transparent),
        onClick = onClick,
    ) {
        Icon(
            painter = painterResource(R.drawable.icon_add_72dp),
            contentDescription = contentDescription,
        )
    }
}