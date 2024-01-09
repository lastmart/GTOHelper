package com.gtohelper.domain.repository

import com.gtohelper.domain.models.Competition
import kotlinx.coroutines.flow.Flow

interface CompetitionRepository {
    fun getAll(): Flow<List<Competition>>
    fun getFlowById(id: Int): Flow<Competition?>
    suspend fun getById(id: Int): Competition?
    suspend fun create(competition: Competition)
    suspend fun update(competition: Competition)
    suspend fun delete(competition: Competition)
    suspend fun deleteById(id: Int)
}