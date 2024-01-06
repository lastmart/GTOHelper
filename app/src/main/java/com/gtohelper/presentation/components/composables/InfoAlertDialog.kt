package com.gtohelper.presentation.components.composables

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
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.window.DialogProperties
import com.gtohelper.R

@Preview
@Composable
fun PreviewInfoAlertDialog(){
    InfoAlertDialog(
        title = "При импортировании",
        description = "номера встретились несколько раз. Пожалуйста, проверьте правильность внесенных данных") {
    }
}

@Composable
fun InfoAlertDialog(
    title: String,
    description: String,
    onOKClicked: () -> Unit,
) {
    Dialog(
        onDismissRequest = onOKClicked,
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true
        )
    ) {
        Card(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.secondary,
                contentColor = MaterialTheme.colorScheme.onSecondary,
            ),
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
                        TextButton(onClick = onOKClicked) {
                            Text(
                                text = stringResource(R.string.ok),
                                fontSize = 16.sp,
                                color = MaterialTheme.colorScheme.tertiaryContainer
                            )
                        }
                    }
                }
            }
        }
    }
}