//package com.gtohelper.presentation.components.composables
//
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material.TextField
//import androidx.compose.material3.Icon
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import com.gtohelper.R
//
//@Preview
//@Composable
//fun PreviewAppSearchView() {
//    AppSearchView(
//        value = "",
//        hint = "Search",
//    )
//}
//
//@Composable
//fun AppSearchView(
//    value: String,
//    onValueChange: (String) -> Unit = {},
//    hint: String?,
//) {
//    TextField(
//        shape = RoundedCornerShape(10.dp),
//        placeholder = { hint?.let { Text(it) } },
//        modifier = Modifier
//            .fillMaxWidth()
//            .background(Color.White),
//        value = value,
//        onValueChange = onValueChange,
//        leadingIcon = {
//            Icon(
//                painter = painterResource(R.drawable.search_gray_24),
//                contentDescription = null
//            )
//        },
//    )
//}