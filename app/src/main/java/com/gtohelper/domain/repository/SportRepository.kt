package com.gtohelper.domain.repository

interface SportRepository {
    suspend fun getCompetitorIds(sport: String): List<Int>
    suspend fun getSports(): List<String>
    suspend fun removeCompetitor(sport: String, id: Int)
    suspend fun addCompetitor(sport: String, id: Int)
}