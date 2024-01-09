package com.gtohelper.domain.usecases.sport_result

data class SportResultUseCases(
    val saveSportResult: SaveSportResultUseCase,
    val editSportResult: EditSportResultUseCase,
    val deleteSportResult: DeleteSportResultUseCase,
    val getResultsAndCompetitors: GetResultsAndCompetitors,
)