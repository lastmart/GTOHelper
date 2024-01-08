package com.gtohelper.presentation.components.transformations

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle


class MirroringVisibleHintTransformation(
    private val hint: String
) : VisualTransformation {

    override fun filter(text: AnnotatedString): TransformedText {
        val transformedText = buildAnnotatedString {
            if (text.length < hint.length) {
                withStyle(SpanStyle(Color.Unspecified.copy(alpha = 0.4f))){
                    append(hint.drop(text.length))
                }
            }
            append(text)
        }
        return TransformedText(
            offsetMapping = createOffsetMapping(text.length, hint.length),
            text = transformedText
        )
    }
    private fun createOffsetMapping(textLength: Int, hintLength: Int): OffsetMapping =
        object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int = hintLength
            override fun transformedToOriginal(offset: Int): Int = textLength
        }
}