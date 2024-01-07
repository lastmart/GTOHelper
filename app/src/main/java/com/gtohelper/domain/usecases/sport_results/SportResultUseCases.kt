package com.gtohelper.domain.usecases.sport_results

data class SportResultUseCases(
    val saveSportResult: SaveSportResultUseCase,
    val getResultsAndCompetitors: GetResultsAndCompetitorsUseCase,
)