package com.gtohelper.presentation.ui.util

import java.util.Locale

fun String.capitalize(): String =
    this.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() }