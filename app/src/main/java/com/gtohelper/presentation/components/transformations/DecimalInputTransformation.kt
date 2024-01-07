package com.gtohelper.presentation.components.transformations

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation


class DecimalInputVisualTransformation(

) : VisualTransformation {

    override fun filter(text: AnnotatedString): TransformedText {

        val inputText = text.text

        val formattedNumber = when (inputText.length) {
            0 -> ""
            1 -> "$inputText."
            2 -> inputText[0] + "." + inputText[1]
            3 -> inputText.substring(0, 2) + "." + inputText[2]
            else -> ""
        }

        val newText = AnnotatedString(
            text = formattedNumber,
            spanStyles = text.spanStyles,
            paragraphStyles = text.paragraphStyles
        )

        val offsetMapping = FixedCursorOffsetMapping(
            contentLength = inputText.length,
            formattedContentLength = formattedNumber.length
        )

        return TransformedText(newText, offsetMapping)
    }
}

private class FixedCursorOffsetMapping(
    private val contentLength: Int,
    private val formattedContentLength: Int,
) : OffsetMapping {
    override fun originalToTransformed(offset: Int): Int = formattedContentLength
    override fun transformedToOriginal(offset: Int): Int = contentLength
}