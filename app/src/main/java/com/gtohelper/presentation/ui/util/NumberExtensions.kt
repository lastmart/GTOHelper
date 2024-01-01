package com.gtohelper.presentation.ui.util


val romanNumerals = mapOf(
    1 to "I",
    2 to "II",
    3 to "III",
    4 to "IV",
    5 to "V",
    6 to "VI",
    7 to "VII",
    8 to "VIII",
    9 to "IX",
    10 to "X",
    11 to "XI",
)

fun Int.toRoman(): String? = romanNumerals[this]

