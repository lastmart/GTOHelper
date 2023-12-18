package com.gtohelper.domain.repository

interface CompetitorResultsRepository {
     suspend fun getDictSportNormative(id: Int): MutableMap<String, MutableMap<String, Double>>
     suspend fun getTotalPoints(id: Int): Double
     suspend fun changeResult(sport:String, oldNormative: String, newNormative:String)
}