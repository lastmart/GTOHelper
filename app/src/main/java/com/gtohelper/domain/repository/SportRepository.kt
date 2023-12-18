package com.gtohelper.domain.repository

interface SportRepository {
    suspend fun getCompetitorIds(sport:String): List<Int>
    suspend fun getSports(): List<String>
    suspend fun removeCompetitor(id: Int)
    suspend fun addCompetitor(id: Int)
}