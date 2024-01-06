package com.gtohelper.data.repository

import com.gtohelper.data.database.discipline.DisciplineDao
import com.gtohelper.data.database.discipline.DisciplineEntity
import com.gtohelper.data.database.relations.CompetitionDisciplineCrossRef
import com.gtohelper.data.database.relations.CompetitionDisciplineDao
import com.gtohelper.data.mappers.toSubDiscipline
import com.gtohelper.domain.models.Discipline
import com.gtohelper.domain.models.DisciplinePointType
import com.gtohelper.domain.repository.DisciplineRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/*
package com.gtohelper.data.repository

import com.gtohelper.R
import com.gtohelper.data.database.discipline.DisciplineDao
import com.gtohelper.data.database.discipline.DisciplineEntity
import com.gtohelper.data.database.discipline.DisciplinesProvider
import com.gtohelper.data.mappers.toSubDiscipline
import com.gtohelper.domain.models.Discipline
import com.gtohelper.domain.models.DisciplinePointType
import com.gtohelper.domain.repository.DisciplineRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class com.gtohelper.data.repository.DisciplineRepositoryImpl(
    private val dao: DisciplineDao,
) : DisciplineRepository {

    private val initedCompetitions = mutableSetOf<Int>()
    override fun getDisciplines(competitionId: Int): Flow<List<Discipline>> {

        GlobalScope.launch(Dispatchers.IO) {
            initDisciplines(competitionId)
        }

        return dao.getDisciplines(competitionId).map { disciplines ->
            val parentData = disciplines
                .filter { it.parentName.isNullOrBlank() || it.parentName == it.name }
                .associateBy({ it.name }, { it })

            return@map disciplines
                .groupBy { it.parentName }
                .map { (parentName, subDisciplines) ->
                    val domainSubDisciplines = subDisciplines.map { it.toSubDiscipline() }

                    parentName ?: return@map null
                    val parent = parentData[parentName] ?: return@map null

                    Discipline(
                        imageResource = parent.imageResource,
                        name = parentName,
                        subDisciplines = domainSubDisciplines.sortedBy { it.name },
                        isSelected = false,
                        type = parent.type
                    )
                }
                .filterNotNull()
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getSelectedDisciplines(competitionId: Int): Flow<List<Discipline>> {
        return getDisciplines(competitionId)
            .flatMapConcat { disciplines ->
                flowOf(
                    disciplines.flatMap { discipline ->
                        discipline.subDisciplines.filter { subDiscipline ->
                            subDiscipline.isSelected
                        }
                    }
                )
            }
    }

    override fun getNotSelectedDisciplines(competitionId: Int): Flow<List<Discipline>> {
        return getDisciplines(competitionId).map { disciplines ->
            disciplines.map { discipline ->
                Discipline(
                    imageResource = discipline.imageResource,
                    name = discipline.name,
                    subDisciplines = discipline.subDisciplines.filter { subDiscipline ->
                        !subDiscipline.isSelected
                    },
                    isSelected = discipline.isSelected,
                    type = discipline.type
                )
            }
        }
    }

    override suspend fun addDisciplineToSelected(discipline: Discipline, competitionId: Int) {
        val disciplineEntity = dao.getDisciplineByName(discipline.name, competitionId) ?: return
        val newDiscipline = disciplineEntity.copy(isSelected = true)

        dao.upsertDiscipline(newDiscipline)
    }

    override suspend fun deleteDisciplineFromSelectedByName(name: String, competitionId: Int) {
        val disciplineEntity = dao.getDisciplineByName(name, competitionId) ?: return
        val newDiscipline = disciplineEntity.copy(isSelected = false)

        dao.upsertDiscipline(newDiscipline)
    }

    private suspend fun initDisciplines(competitionId: Int) {
        dao.getDisciplines(competitionId).collect {
            if (it.isNotEmpty() || !initedCompetitions.add(competitionId)) {
                return@collect
            }

            //dao.upsertDisciplines(
            //    DisciplinesProvider.disciplines
            //)
        }
    }

}
*/


class DisciplineRepositoryImpl(
    private val dao: DisciplineDao,
    private val competitionDisciplineDao: CompetitionDisciplineDao
) : DisciplineRepository {

    override fun getDisciplines(): Flow<List<Discipline>> {
        return dao.getDisciplines()
            .map { disciplineEntities ->
                mapEntitiesToDomainModels(disciplineEntities)
            }
    }

    override fun getSelectedDisciplines(competitionId: Int): Flow<List<Discipline>> {

        /*val disciplineEntities = competitionDisciplineDao.getDisciplines().filter {
            it.competition.id == competitionId
        }.flatMap { it.disciplines }.also {
            println("Selected = ${it.map { it.name }}")
        }

        emit(mapEntitiesToDomainModels(disciplineEntities))
    }*/

        return competitionDisciplineDao.getDisciplines().map { list ->
            list.filter { competitionWithDisciplines ->
                competitionWithDisciplines.competition.id == competitionId
            }.flatMap { competitionWithDisciplines ->
                mapEntitiesToDomainModels(
                    competitionWithDisciplines.disciplines
                )
            }.also {
                println()
                println()
                println("Selected:")
                it.forEach {
                    println(it)
                }
                println()
                println()
            }
        }
    }

    override fun getNotSelectedDisciplines(competitionId: Int): Flow<List<Discipline>> {
        return getSelectedDisciplines(competitionId).map { selectedDisciplines ->
            val allDisciplinesEntities = dao.getAllDisciplines()
            val allDisciplines = mapEntitiesToDomainModels(allDisciplinesEntities)

            val selectedDisciplinesNames = selectedDisciplines.map { it.name }.toSet()


            return@map allDisciplines.filter {
                //    println("${it.name} is selected? ${}")
                it.name !in selectedDisciplinesNames
            }
        }
    }

    override suspend fun addDisciplineToSelected(discipline: Discipline, competitionId: Int) {
        println("Add $competitionId : ${discipline.name}")

        val disciplineEntity = getDisciplineByName(discipline.name)

        disciplineEntity?.let {
            competitionDisciplineDao.insert(
                CompetitionDisciplineCrossRef(
                    competitionId = competitionId,
                    disciplineId = it.id
                )
            )
        }
    }

    override suspend fun deleteDisciplineFromSelectedByName(name: String, competitionId: Int) {
        val disciplineEntity = getDisciplineByName(name)

        disciplineEntity?.let {
            competitionDisciplineDao.delete(
                CompetitionDisciplineCrossRef(
                    competitionId = competitionId,
                    disciplineId = it.id
                )
            )
        }
    }

    private fun mapEntitiesToDomainModels(disciplineEntities: List<DisciplineEntity>): List<Discipline> {

        println("Disciplines to map: ${disciplineEntities}")


        //     val (parents, disciplines) = disciplineEntities.partition { it.name == it.parentName }

        return disciplineEntities.map { disciplineEntity ->

            val subDisciplines = disciplineEntities
                .filter {
                    it.parentName == disciplineEntity.name
                            && it.name != it.parentName
                }
                .map { it.toSubDiscipline() }


            println("Parent Name: ${disciplineEntity.name}")
            println("SubDisciplines :${subDisciplines}")

            Discipline(
                name = disciplineEntity.name,
                imageResource = disciplineEntity.imageResource,
                subDisciplines = subDisciplines,
                type = disciplineEntity.type
            )
        }
    }

    private suspend fun getDisciplineByName(name: String): DisciplineEntity? {
        return dao.getDisciplineByName(name)
    }
}