package com.gtohelper.presentation.ui.competitors_results

import com.gtohelper.domain.models.Competitor

data class CompetitorsResultsUIState(
    val competitorsResults: List<Pair<Competitor, Int>>
)