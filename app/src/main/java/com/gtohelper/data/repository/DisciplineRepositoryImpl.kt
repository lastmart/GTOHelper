package com.gtohelper.data.repository

import com.gtohelper.data.database.discipline.DisciplineDao
import com.gtohelper.data.database.discipline.SubDisciplineEntity
import com.gtohelper.data.database.relations.CompetitionSubDisciplineCrossRef
import com.gtohelper.data.database.relations.CompetitionSubDisciplineDao
import com.gtohelper.data.mappers.toDomainDiscipline
import com.gtohelper.data.mappers.toDomainSubDiscipline
import com.gtohelper.domain.models.Discipline
import com.gtohelper.domain.models.SubDiscipline
import com.gtohelper.domain.repository.DisciplineRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class DisciplineRepositoryImpl(
    private val disciplineDao: DisciplineDao,
    private val competitionSubDisciplineDao: CompetitionSubDisciplineDao
) : DisciplineRepository {
    override suspend fun getBy(name: String): Discipline? {
        return withContext(Dispatchers.IO) {
            competitionSubDisciplineDao.getBy(name)?.toDomainDiscipline()
        }
    }

    override suspend fun getAllSelectedSubDisciplines(competitionId: Int): List<SubDiscipline> {
        return withContext(Dispatchers.IO) {
            getSelectedSubDisciplines(competitionId).firstOrNull()
                ?.map { it.toDomainSubDiscipline() } ?: listOf()
        }
    }

    override fun getDisciplines(): Flow<List<Discipline>> {
        return disciplineDao.getSubDisciplines().map { subDisciplinesEntities ->
            groupSubDisciplinesToDisciplines(subDisciplinesEntities)
        }
    }

    override fun getSelectedDisciplines(competitionId: Int): Flow<List<Discipline>> {
        return getSelectedSubDisciplines(competitionId).map { subDisciplines ->

            subDisciplines ?: return@map null
            return@map groupSubDisciplinesToDisciplines(subDisciplines)

        }.filterNotNull()
    }

    private fun getSelectedSubDisciplines(competitionId: Int): Flow<List<SubDisciplineEntity>?> {
        return competitionSubDisciplineDao.getCompetitionWithSubDisciplines().map { list ->
            list.firstOrNull { it.competition.id == competitionId }
                ?.subDisciplines
        }
    }

    override fun getNotSelectedDisciplines(competitionId: Int): Flow<List<Discipline>> {
        val subDisciplinesFlow = disciplineDao.getSubDisciplines()
        val selectedSubDisciplinesFlow = getSelectedSubDisciplines(competitionId)

        return subDisciplinesFlow.combine(selectedSubDisciplinesFlow) { allSubDisciplines, selectedSubDisciplines ->

            if (selectedSubDisciplines == null)
                return@combine groupSubDisciplinesToDisciplines(allSubDisciplines)

            val notSelectedSubDisciplines = allSubDisciplines - selectedSubDisciplines

            return@combine groupSubDisciplinesToDisciplines(notSelectedSubDisciplines)
        }
    }

    private suspend fun groupSubDisciplinesToDisciplines(subDisciplines: List<SubDisciplineEntity>): List<Discipline> {

        val parentsNamesWithSubDisciplines = subDisciplines.groupBy { it.parentName }

        return parentsNamesWithSubDisciplines.map { (parentName, subDisciplines) ->
            val parentEntity = disciplineDao.getDisciplineByName(parentName)
                ?: return@map null

            Discipline(
                name = parentName,
                imageResource = parentEntity.imageResource,
                subDisciplines = subDisciplines.map { it.toDomainSubDiscipline() },
                type = parentEntity.type
            )
        }.filterNotNull()
    }

    override suspend fun addSubDisciplineToSelectedByName(name: String, competitionId: Int) {
        val subDisciplineEntity = disciplineDao.getSubDisciplineByName(name)
        val subDisciplineId = subDisciplineEntity?.id

        subDisciplineId?.let {
            competitionSubDisciplineDao.insert(
                CompetitionSubDisciplineCrossRef(
                    competitionId = competitionId,
                    subDisciplineId = it
                )
            )
        }
    }

    override suspend fun deleteSubDisciplineFromSelectedByName(name: String, competitionId: Int) {
        val subDisciplineEntity = disciplineDao.getSubDisciplineByName(name)
        val subDisciplineId = subDisciplineEntity?.id

        subDisciplineId?.let {
            competitionSubDisciplineDao.delete(
                CompetitionSubDisciplineCrossRef(
                    competitionId = competitionId,
                    subDisciplineId = it
                )
            )
        }
    }
}