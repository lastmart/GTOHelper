package com.gtohelper.presentation.ui.competitors_list.components.forms

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

class TimeTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val number = text.text.toIntOrNull() ?: 0
        val hour = (number / 3600).toString().padStart(2, '0')
        val minute = (number % 3600 / 60).toString().padStart(2, '0')
        val second = (number % 60).toString().padStart(2, '0')

        val result = "$hour:$minute:$second"
        val newText = AnnotatedString(
            text = result,
            spanStyles = text.spanStyles,
            paragraphStyles = text.paragraphStyles
        )

        val offsetMapping = FixedCursorOffsetMapping(
            contentLength = text.length,
            formattedContentLength = result.length
        )

        return TransformedText(newText, offsetMapping)
    }

    private class FixedCursorOffsetMapping(
        private val contentLength: Int,
        private val formattedContentLength: Int,
    ) : OffsetMapping {
        override fun originalToTransformed(offset: Int): Int = formattedContentLength
        override fun transformedToOriginal(offset: Int): Int = contentLength
    }
}