package com.gtohelper.domain.models

data class CompetitorWithResults(
    val competitor: Competitor,
    val results: List<SportResult>,
)