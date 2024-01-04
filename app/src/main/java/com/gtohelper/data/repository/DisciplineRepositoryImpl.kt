package com.gtohelper.data.repository

import com.gtohelper.R
import com.gtohelper.data.database.discipline.DisciplineDao
import com.gtohelper.data.database.discipline.DisciplineEntity
import com.gtohelper.data.mappers.toSubDiscipline
import com.gtohelper.domain.models.Discipline
import com.gtohelper.domain.repository.DisciplineRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class DisciplineRepositoryImpl(
    private val dao: DisciplineDao,
) : DisciplineRepository {

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
        dao.getDisciplines(competitionId).collect() {
            if (it.isNotEmpty()) return@collect

            dao.upsertDisciplines(
                listOf(
                    DisciplineEntity(
                        competitionId = competitionId,
                        name = "Бег на короткие дистанции",
                        imageResource = R.drawable.discipline_sprinting
                    ),
                    DisciplineEntity(
                        competitionId = competitionId,
                        name = "Бег на 30 м",
                        parentName = "Бег на короткие дистанции",
                        imageResource = R.drawable.sub_discipline_sprinting_30m
                    ),
                    DisciplineEntity(
                        competitionId = competitionId,
                        name = "Бег на 60 м",
                        parentName = "Бег на короткие дистанции",
                        imageResource = R.drawable.sub_discipline_sprinting_60m
                    ),
                    DisciplineEntity(
                        competitionId = competitionId,
                        name = "Бег на 100 м",
                        parentName = "Бег на короткие дистанции",
                        imageResource = R.drawable.sub_discipline_sprinting_100m
                    ),
                    DisciplineEntity(
                        competitionId = competitionId,
                        name = "Бег на длинные дистанции",
                        imageResource = R.drawable.discipline_long_distance_running
                    ),
                    DisciplineEntity(
                        competitionId = competitionId,
                        name = "Бег на 1 км",
                        parentName = "Бег на длинные дистанции",
                        imageResource = R.drawable.sub_discipline_long_distance_running_1km
                    ),
                    DisciplineEntity(
                        competitionId = competitionId,
                        name = "Бег на 1.5 км",
                        parentName = "Бег на длинные дистанции",
                        imageResource = R.drawable.sub_discipline_long_distance_running_1dot5km
                    ),
                    DisciplineEntity(
                        competitionId = competitionId,
                        name = "Бег на 2 км",
                        parentName = "Бег на длинные дистанции",
                        imageResource = R.drawable.sub_discipline_long_distance_running_2km
                    ),
                    DisciplineEntity(
                        competitionId = competitionId,
                        name = "Бег на 3 км",
                        parentName = "Бег на длинные дистанции",
                        imageResource = R.drawable.sub_discipline_long_distance_running_3km
                    ),
                    DisciplineEntity(
                        competitionId = competitionId,
                        name = "Кросс по пересечённой местности",
                        parentName = "Кросс по пересечённой местности",
                        imageResource = R.drawable.discipline_cross_country,
                    ),
                    DisciplineEntity(
                        competitionId = competitionId,
                        name = "Смешанное передвижение",
                        parentName = "Смешанное передвижение",
                        imageResource = R.drawable.discipline_mixed_movement,
                    ),
                    DisciplineEntity(
                        competitionId = competitionId,
                        name = "Челночный бег",
                        parentName = "Челночный бег",
                        imageResource = R.drawable.discipline_shuttle_run,
                    ),
                    DisciplineEntity(
                        competitionId = competitionId,
                        name = "Скандинавская ходьба",
                        parentName = "Скандинавская ходьба",
                        imageResource = R.drawable.discipline_nordic_walking,
                    ),
                    DisciplineEntity(
                        competitionId = competitionId,
                        name = "Передвижение на лыжах",
                        parentName = "Передвижение на лыжах",
                        imageResource = R.drawable.discipline_movement_on_skis,
                    ),
                    DisciplineEntity(
                        competitionId = competitionId,
                        name = "Бег на лыжах",
                        parentName = "Бег на лыжах",
                        imageResource = R.drawable.discipline_skiing,
                    ),
                    DisciplineEntity(
                        competitionId = competitionId,
                        name = "Подтягивание из виса на высокой перекладине",
                        parentName = "Подтягивание из виса на высокой перекладине",
                        imageResource = R.drawable.discipline_human_pull_up_top,
                    ),
                    DisciplineEntity(
                        competitionId = competitionId,
                        name = "Подтягивание из виса лежа на низкой перекладине",
                        parentName = "Подтягивание из виса лежа на низкой перекладине",
                        imageResource = R.drawable.discipline_human_pull_up_bottom,
                    ),
                    DisciplineEntity(
                        competitionId = competitionId,
                        name = "Сгибание и разгибание рук",
                        parentName = "Сгибание и разгибание рук",
                        imageResource = R.drawable.discipline_flexion_extension_arms,
                    ),
                    DisciplineEntity(
                        competitionId = competitionId,
                        name = "Наклон вперед",
                        parentName = "Наклон вперед",
                        imageResource = R.drawable.discipline_lean_forward,
                    ),
                    DisciplineEntity(
                        competitionId = competitionId,
                        name = "Прыжок в длину с места",
                        parentName = "Прыжок в длину с места",
                        imageResource = R.drawable.discipline_standing_long_jump,
                    ),
                    DisciplineEntity(
                        competitionId = competitionId,
                        name = "Прыжок в длину с разбега",
                        parentName = "Прыжок в длину с разбега",
                        imageResource = R.drawable.discipline_running_long_jump,
                    ),
                    DisciplineEntity(
                        competitionId = competitionId,
                        name = "Поднимание туловища",
                        parentName = "Поднимание туловища",
                        imageResource = R.drawable.discipline_lifting_body,
                    ),
                    DisciplineEntity(
                        competitionId = competitionId,
                        name = "Плавание",
                        parentName = "Плавание",
                        imageResource = R.drawable.discipline_swimming,
                    ),
                    DisciplineEntity(
                        competitionId = competitionId,
                        name = "Стрельба",
                        parentName = "Стрельба",
                        imageResource = R.drawable.discipline_shooting,
                    ),
                    DisciplineEntity(
                        competitionId = competitionId,
                        name = "Рывок гири",
                        parentName = "Рывок гири",
                        imageResource = R.drawable.discipline_kettlebell_snatch,
                    ),
                    DisciplineEntity(
                        competitionId = competitionId,
                        name = "Метание",
                        parentName = "Метание",
                        imageResource = R.drawable.discipline_throwing,
                    ),
                    DisciplineEntity(
                        competitionId = competitionId,
                        name = "Метание мяча в цель",
                        parentName = "Метание мяча в цель",
                        imageResource = R.drawable.discipline_ball_throwing_at_target,
                    ),
                )
            )
        }
    }

}
