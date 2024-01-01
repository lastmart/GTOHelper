package com.gtohelper.presentation.ui.competitors_list

import com.gtohelper.domain.models.Competitor


data class CompetitorListUiState(
    val competitors: List<Competitor> = listOf(),
)