package com.gtohelper.presentation.components.transformations

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle


class VisibleHintTransformation(
    private val hint: String
) : VisualTransformation {

    override fun filter(text: AnnotatedString): TransformedText {
        val transformedText = buildAnnotatedString {

            append(text)
            if (text.length < hint.length) {
                withStyle(SpanStyle(Color.Gray)){
                    append(hint.drop(text.length))
                }
            }
        }
        return TransformedText(
            offsetMapping = createOffsetMapping(hint.length),
            text = transformedText
        )
    }

    private fun createOffsetMapping(suggestionLength: Int): OffsetMapping {
        return object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                return offset
            }

            override fun transformedToOriginal(offset: Int): Int {
                return if (offset <= suggestionLength) offset else offset - suggestionLength
            }
        }
    }
}