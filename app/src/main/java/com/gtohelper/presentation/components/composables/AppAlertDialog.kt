package com.gtohelper.presentation.components.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.gtohelper.R
import com.gtohelper.presentation.ui.theme.BorderColor
import com.gtohelper.presentation.ui.theme.Green
import com.gtohelper.presentation.ui.theme.Red

@Composable
fun AppAlertDialogRoute(
    title: String,
    description: String,
    onOKClicked: () -> Unit,
    onCancelClicked: () -> Unit

) {
    AppAlertDialog(
        title = title,
        description = description,
        onOKClicked = onOKClicked,
        onCancelClicked = onCancelClicked
    )
}

@Composable
fun AppAlertDialog(
    title: String,
    description: String,
    onOKClicked: () -> Unit,
    onCancelClicked: () -> Unit
) {
    Dialog(onDismissRequest = onCancelClicked) {
        Card(
            border = BorderStroke(1.dp, BorderColor),
            shape = RoundedCornerShape(20.dp)
        ) {
            Box(
                modifier = Modifier
                    .padding(10.dp),
            ) {

                Column {
                    Text(
                        text = title,
                        fontSize = 25.sp,
                        color = Color.Black
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = description,
                        fontSize = 16.sp,
                        color = Color.Black,
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        TextButton(onClick = onCancelClicked) {
                            Text(
                                text = stringResource(id = R.string.cancel),
                                fontSize = 16.sp,
                                color = Green
                            )
                        }

                        TextButton(onClick = onOKClicked) {
                            Text(
                                text = stringResource(id = R.string.delete),
                                fontSize = 16.sp,
                                color = Red
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun AppAlertDialogPreview() {
    AppAlertDialog(
        title = "Удалить что-то",
        description = "Вы действительно хотите удалить\nчто-то?",
        onOKClicked = {},
        onCancelClicked = {}
    )
}