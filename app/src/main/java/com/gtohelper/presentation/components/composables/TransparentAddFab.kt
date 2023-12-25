package com.gtohelper.presentation.components.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gtohelper.R

@Preview
@Composable
fun PreviewTransparentAddFab() {
    TransparentAddFab(
        onClick = { },
        contentDescription = null,
    )
}

@Composable
fun TransparentAddFab(
    onClick: () -> Unit,
    contentDescription: String?,
) {
    IconButton(
        colors = IconButtonDefaults.iconButtonColors(
            disabledContainerColor = Color.Transparent,
            disabledContentColor = Color.Transparent,
        ),
        modifier = Modifier
            .background(Color.Transparent)
            .size(64.dp),
        onClick = onClick,
    ) {
        Icon(
            painter = painterResource(R.drawable.icon_add_72dp),
            contentDescription = contentDescription,
        )
    }
}