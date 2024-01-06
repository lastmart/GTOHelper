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
    12 to "XII",
    13 to "XIII",
    14 to "XIV",
    15 to "XV",
    16 to "XVI",
    17 to "XVII",
    18 to "XVIII"
)

fun Int.toRoman(): String? = romanNumerals[this]

