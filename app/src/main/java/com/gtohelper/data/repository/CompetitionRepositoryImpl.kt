package com.gtohelper.data.repository

import com.gtohelper.data.database.competition.CompetitionDao
import com.gtohelper.data.mappers.toDomainModel
import com.gtohelper.data.mappers.toEntity
import com.gtohelper.domain.models.Competition
import com.gtohelper.domain.repository.CompetitionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class CompetitionRepositoryImpl(
    private val dao: CompetitionDao,
) : CompetitionRepository {
    override fun getAll(): Flow<List<Competition>> {
        return dao.getAll().map { it.map { list -> list.toDomainModel() } }
    }

    override suspend fun getById(id: Int): Competition? {
        return withContext(Dispatchers.IO) {
            dao.getById(id)?.toDomainModel()
        }
    }

    override suspend fun create(competition: Competition) {
        withContext(Dispatchers.IO) {
            dao.create(competition.toEntity())
        }
    }

    override suspend fun update(competition: Competition) {
        withContext(Dispatchers.IO) {
            dao.update(competition.toEntity())
        }
    }

    override suspend fun delete(competition: Competition) {
        withContext(Dispatchers.IO) {
            dao.delete(competition.toEntity())
        }
    }

    override suspend fun deleteById(id: Int) {
        withContext(Dispatchers.IO) {
            val competitionToDelete = getById(id)

            competitionToDelete?.let {
                delete(it)
            }
        }
    }
}